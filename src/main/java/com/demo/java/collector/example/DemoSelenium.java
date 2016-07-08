package com.demo.java.collector.example;

import com.demo.java.collector.crawldb.DBManager;
import com.demo.java.collector.crawler.Crawler;
import com.demo.java.collector.fetcher.Executor;
import com.demo.java.collector.model.CrawlDatum;
import com.demo.java.collector.model.CrawlDatums;
import com.demo.java.collector.plugin.berkeley.BerkeleyDBManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * 本教程演示如何利用WebCollector爬取javascript生成的数据
 */
public class DemoSelenium {

    public static void main(String[] args) throws Exception {
        Executor executor = new Executor() {
            @Override
            public void execute(CrawlDatum datum, CrawlDatums next) throws Exception {
                HtmlUnitDriver driver = new HtmlUnitDriver();
                driver.setJavascriptEnabled(true);
                driver.get(datum.getUrl());
                WebElement element = driver.findElementByCssSelector("span#outlink1");
                System.out.println("反链数:" + element.getText());
            }
        };
        //创建一个基于伯克利DB的DBManager
        DBManager manager = new BerkeleyDBManager("crawl");
        //创建一个Crawler需要有DBManager和Executor
        Crawler crawler = new Crawler(manager, executor);
        crawler.addSeed("http://seo.chinaz.com/?host=www.tuicool.com");
        crawler.start(1);
    }
}
