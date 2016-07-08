package com.demo.java.collector.example;

import com.demo.java.collector.model.CrawlDatums;
import com.demo.java.collector.model.Page;
import com.demo.java.collector.plugin.berkeley.BreadthCrawler;
import com.demo.java.dao.CarDao;
import com.demo.java.model.Car;
import com.demo.java.utils.SpringContextUtil;
import com.demo.java.utils.crypto.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CarCrawler extends BreadthCrawler {

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
        Elements dates = page.doc().body().select("div#infolist table.tbimg tr");
        for (Element e : dates) {
            if (e.select("td.t a") == null || e.select("td.t a").size() == 0) {
                continue;
            }
            Car car = new Car();
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
            car.setId(DigestUtils.md5B64(car.getTitle()));
            carDao.save(car);
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

    public static void start(String seed, String regex) throws Exception {
        start(seed, regex, Integer.MAX_VALUE, 50);
    }

    public static void main(String[] args) throws Exception {
        CarCrawler.start("http://bj.58.com/ershouche"
                , "http://bj.58.com/ershouche/pn[0-9]/.*"
                , 1, 10);
    }
}
