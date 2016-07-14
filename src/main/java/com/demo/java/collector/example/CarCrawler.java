package com.demo.java.collector.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.java.collector.model.CrawlDatums;
import com.demo.java.collector.model.Page;
import com.demo.java.collector.plugin.berkeley.BreadthCrawler;
import com.demo.java.model.Car;
import com.demo.java.model.Regex;
import com.demo.java.service.CarService;
import com.demo.java.utils.PatternUtils;
import com.demo.java.utils.SpringContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;

import java.util.Map;

public class CarCrawler extends BreadthCrawler {

    private static Regex regex;

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
        if (page.matchUrl(regex.getRegex())) {
            Document doc = page.doc();
            for (String ik : regex.ignoreKeys()) {
                if (doc.select(ik).size() > 0) {
                    return;
                }
            }
            JSONObject object = new JSONObject();
            for (Map.Entry<String, Object> entry : regex.getJSONData().entrySet()) {
                JSONObject jsonVal = (JSONObject) entry.getValue();
                String dom = jsonVal.getString("dom");
                if (StringUtils.isBlank(dom)) continue;
                String type = jsonVal.getString("type");
                if (StringUtils.isBlank(type)) type = "String";
                String[] domes = dom.split("\\|");
                String val = "";
                for (String d : domes) {
                    val = doc.select(d).eq(0).text();
                    if (StringUtils.isNoneBlank(val)) {
                        break;
                    }
                }
                if (type.equals("Integer")) {
                    val = PatternUtils.matchNum(val);
                }
                object.put(entry.getKey(), val);
            }
            Car car = JSON.toJavaObject(object, Car.class);
            if (car != null && StringUtils.isNoneBlank(car.getCarName())) {
                String url = page.getUrl();
                car.setId(PatternUtils.matchInteger(url, 1));
                car.setUrl(url);
                carService.saveOrUpdate(car);
            }
        }
    }

    public static void start(String seed, String regex, int start, int threads) throws Exception {
        CarCrawler crawler = new CarCrawler("crawler", true);
        crawler.addSeed(seed);
        crawler.addRegex(regex);
        crawler.setThreads(threads);
        crawler.start(start);
    }

    public static void start(Regex t) throws Exception {
        regex = t;
        start(regex.getSeed(), regex.getRegex(), regex.getStart(), regex.getThread());
    }

    public static void main(String[] args) throws Exception {
        Regex t = new Regex();
//        t.setSeed("http://m.58.com/bj/ershouche/");
//        t.setRegex("http://m.58.com/bj/ershouche/(\\d+)x.shtml(.*)");
        t.setSeed("http://bj.58.com/ershouche");
        t.setRegex("http://bj.58.com/ershouche/[0-9]+x.shtml(.*)");
        t.setStart(5);
        t.setThread(30);
        start(t);
    }
}
