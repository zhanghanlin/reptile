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
import com.demo.java.utils.crypto.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CarCrawler extends BreadthCrawler {

    CarDao carDao = (CarDao) SpringContextUtil.getBean("carDao");

    private static JSONObject json;

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
        if (json == null) return;
        Elements dates = page.doc().body().select(json.getString("select"));
        for (Element e : dates) {
            if (e.select("td.t a") == null || e.select("td.t a").size() == 0) {
                continue;
            }
            JSONObject object = new JSONObject();
            for (Map.Entry<String, Object> entry : json.getJSONObject("data").entrySet()) {
                JSONObject jsonVal = (JSONObject) entry.getValue();
                String attr = jsonVal.getString("attr");
                int type = jsonVal.getInteger("type");
                String select = jsonVal.getString("select");
                String val = e.select(select).first().text();
                if (StringUtils.isNoneBlank(attr)) {
                    String[] attrs = attr.split(",");
                    for (String a : attrs) {
                        val = e.select(select).attr(a);
                        if (StringUtils.isNoneBlank(val)) {
                            break;
                        }
                    }
                }
                if (type == 2) {
                    String split = jsonVal.getString("split");
                    int index = jsonVal.getInteger("index");
                    String[] valArr = val.split(split);
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
                car.setId(DigestUtils.md5B64(car.getTitle()));
            }
            /* Car car = new Car();
            car.setTitle(e.select("td.t a").first().text());
            String imgSrc = e.select("td.img img").attr("lazy_src");
            if (StringUtils.isBlank(imgSrc)) {
                imgSrc = e.select("td.img img").attr("src");
            }
            car.setImg(imgSrc);
            String yk[] = e.select("td.t p").text().replaceAll("\\s*", "").split("/");
            if (yk.length > 0)
                car.setYear(patternNum(yk[0]));
            if (yk.length > 1)
                car.setKilometre(patternNum(yk[1]));
            if (yk.length > 2)
                car.setLiter(patternNum(yk[2]));
            if (yk.length > 3)
                car.setType(yk[3]);
            car.setPrice(e.select("td.tc b").text());
            car.setId(DigestUtils.md5B64(car.getTitle()));*/
            carDao.saveOrUpdate(car);
        }
    }

    static String patternNum(String str) {
        String regex = "\\d+(\\.\\d+)?";
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
//        json = JSONObject.parseObject(StringEscapeUtils.unescapeHtml4(regex.getData()));
        json = regex.getJSONData();
        start(regex.getSeed(), regex.getRegex(), regex.getStart(), regex.getThread());
    }

    public static void main(String[] args) throws Exception {
        /*CarCrawler.start("http://bj.58.com/ershouche"
                , "http://bj.58.com/ershouche/pn[0-9]/.*"
                , 1, 10);*/
    }
}
