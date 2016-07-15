package com.demo.java.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.java.collector.model.CrawlDatum;
import com.demo.java.collector.model.CrawlDatums;
import com.demo.java.collector.model.Page;
import com.demo.java.collector.net.HttpRequest;
import com.demo.java.collector.net.HttpResponse;
import com.demo.java.collector.net.Proxys;
import com.demo.java.collector.plugin.berkeley.BreadthCrawler;
import com.demo.java.collector.util.FileUtils;
import com.demo.java.common.utils.PatternUtils;
import com.demo.java.common.utils.SpringContextUtil;
import com.demo.java.model.Car;
import com.demo.java.model.Regex;
import com.demo.java.service.CarService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class CarCrawler extends BreadthCrawler {

    private static Regex regex;
    private static Proxys proxys = new Proxys();

    CarService carService = (CarService) SpringContextUtil.getBean("carService");

    /**
     * 构造一个基于伯克利DB的爬虫
     * 伯克利DB文件夹为crawlPath，crawlPath中维护了历史URL等信息
     * 不同任务不要使用相同的crawlPath
     * 两个使用相同crawlPath的爬虫并行爬取会产生错误
     *
     * @param crawlPath 伯克利DB使用的文件夹
     * @param autoParse 是否根据设置的正则自动探测新URL
     */
    public CarCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        //判断地址是否符合规则
        if (page.matchUrl(regex.getRegex()) && regex.getIsData() == 1) {
            Document doc = page.doc();
            //如果页面不存在跳过
            if (doc == null) return;
            //判断是否存在需要忽略的元素
            if (StringUtils.isNoneBlank(regex.getIgnoreKey())) {
                //如果存在则不抓取该页面
                for (String ik : regex.ignoreKeys()) {
                    if (doc.select(ik).size() > 0) {
                        return;
                    }
                }
            }
            JSONObject object = new JSONObject();
            //处理自定义字段信息
            for (Map.Entry<String, Object> entry : regex.getJSONData().entrySet()) {
                JSONObject jsonVal = (JSONObject) entry.getValue();
                //字段CSS选择器
                String dom = jsonVal.getString("dom");
                //如果选择器为空则跳过该字段
                if (StringUtils.isBlank(dom)) continue;
                //字段类型,用于判断是否需要特殊处理
                String type = jsonVal.getString("type");
                String val = getValByDom(dom, doc);
                //默认字段类型String
                if (StringUtils.isBlank(type)) type = "String";
                val = parseType(val, type);
                //特殊处理器
                String handle = jsonVal.getString("handle");
                //需要处理的值,如替换则该字段为需要替换的值
                String handle_p = jsonVal.getString("handle_p");
                val = handle(val, handle, handle_p);
                object.put(entry.getKey(), val.trim());
            }
            //将JSON数据转化为Bean对象
            Car car = JSON.toJavaObject(object, Car.class);
            if (car != null && StringUtils.isNoneBlank(car.getCarName())) {
                String url = page.getUrl();
                //使用URL中最后一段数字作为ID
                car.setId(regex.getTaskKey() + "_" + PatternUtils.matchInteger(url, 1));
                car.setUrl(url);
                car.setSource(regex.getTaskKey());
                carService.saveOrUpdate(car);
            }
        }
    }

    /**
     * 根据Dom选择器获取元素Val
     *
     * @param dom
     * @param doc
     * @return
     */
    static String getValByDom(String dom, Document doc) {
        //DOM选择器可以设置多个'|'分割
        String[] domes = dom.split("\\|");
        String val = "";
        //根据选择器循环获取数据,如果获取到数据则跳出循环
        for (String d : domes) {
            val = doc.select(d).eq(0).text();
            if (StringUtils.isNoneBlank(val)) {
                break;
            }
        }
        return val;
    }

    /**
     * 根据Type处理Val
     *
     * @param val
     * @param type
     * @return
     */
    static String parseType(String val, String type) {
        //判断是否是数字类型
        if (type.equals("Integer")) {
            val = PatternUtils.matchNum(val);
        }
        return val;
    }

    /**
     * 处理器
     *
     * @param val
     * @param handle
     * @param handle_p
     * @return
     */
    static String handle(String val, String handle, String handle_p) {
        //判断处理器是否为空
        if (StringUtils.isNoneBlank(handle)) {
            if (handle.equals("replace")) {
                String[] hps = handle_p.split("\\|");
                for (String h : hps) {
                    val = val.replace(h, "");
                }
            }
        }
        return val;
    }

    /**
     * 设置随机代理对象
     *
     * @param crawlDatum
     * @return
     * @throws Exception
     */
    public HttpResponse getResponse(CrawlDatum crawlDatum) throws Exception {
        HttpRequest request = new HttpRequest(crawlDatum);
        if (regex.getIsProxy() == 1 && proxys.size() > 0) {
            request.setProxy(proxys.nextRandom());
        }
        return request.getResponse();
    }

    /**
     * 设置代理IP
     */
    public static void setProxys() {
        String classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        File file = new File(classPath + "/proxy");
        try {
            String ip = FileUtils.readFile(file, "UTF-8");
            String ips[] = ip.split("\r\n");
            for (String i : ips) {
                if (!proxys.contains(i)) {
                    proxys.add(i);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动抓取任务
     *
     * @param t
     * @throws Exception
     */
    public static void start(Regex t) throws Exception {
        regex = t;
        if (t.getIsProxy() == 1) {
            setProxys();
        }
        CarCrawler crawler = new CarCrawler(t.getTaskKey(), true);
        crawler.addSeed(t.getSeed());
        crawler.addRegex(t.getRegex());
        crawler.setThreads(t.getThread());
        crawler.start(t.getStart());
    }

    /**
     * 停止
     * @param t
     */
    public static void stop(Regex t) {
        CarCrawler crawler = new CarCrawler(t.getTaskKey(), true);
        crawler.stop();
    }

    public static void main(String[] args) throws Exception {
        Regex t = new Regex();
        t.setSeed("http://bj.58.com/ershouche");
        t.setRegex("http://bj.58.com/ershouche/[0-9]+x.shtml(.*)");
        t.setStart(2);
        t.setTaskKey("test58");
        t.setThread(1);
        t.setIsProxy(1);
        start(t);
    }
}
