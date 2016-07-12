package com.demo.java.collector.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.java.collector.model.CrawlDatums;
import com.demo.java.collector.model.Page;
import com.demo.java.collector.plugin.berkeley.BreadthCrawler;
import com.demo.java.dao.CarDao;
import com.demo.java.model.Car;
import com.demo.java.model.Regex;
import com.demo.java.utils.SpringContextUtil;
import org.jsoup.nodes.Document;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CarCrawler extends BreadthCrawler {

    private static JSONObject json;

    CarDao carDao = (CarDao) SpringContextUtil.getBean("carDao");

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
        Document doc = page.doc();
        JSONObject object = new JSONObject();
        for (Map.Entry<String, Object> entry : json.entrySet()) {
            if (entry.getKey().equals("id")) continue;
            JSONObject jsonVal = (JSONObject) entry.getValue();
            int type = jsonVal.getInteger("type");
            String select = jsonVal.getString("select");
            String val = doc.select(select).text();
            if (type == 2) {
                String split = jsonVal.getString("split");
                int index = jsonVal.getInteger("index");
                String[] valArr = val.split("\\" + split);
                if (valArr.length > index - 1) {
                    val = valArr[index - 1].trim();
                } else {
                    val = "";
                }
            }
            object.put(entry.getKey(), val);
        }
        Car car = JSON.toJavaObject(object, Car.class);
        if (car != null) {
            car.setId(patternId(page.getUrl()));
            carDao.saveOrUpdate(car);
        }
    }

    static String patternId(String str) {
        String regex = "\\d+[a-z]";
        Matcher matcher = Pattern.compile(regex).matcher(str);
        if (matcher.find())
            return matcher.group();
        return "";
    }

    public static void start(String seed, String regex, int start, int threads) throws Exception {
        CarCrawler crawler = new CarCrawler("crawler", true);
        crawler.addSeed(seed);
        crawler.addRegex(regex);
        crawler.setThreads(threads);
        crawler.start(start);
    }

    public static void start(Regex regex) throws Exception {
        json = regex.getJSONData();
        start(regex.getSeed(), regex.getRegex(), regex.getStart(), regex.getThread());
    }
}
