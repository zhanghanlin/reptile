package com.demo.java.collector.example;

import com.demo.java.collector.crawldb.DBManager;
import com.demo.java.collector.crawler.Crawler;
import com.demo.java.collector.fetcher.Executor;
import com.demo.java.collector.model.CrawlDatum;
import com.demo.java.collector.model.CrawlDatums;
import com.demo.java.collector.plugin.berkeley.BerkeleyDBManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.List;

/**
 * 本教程演示如何利用WebCollector爬取javascript生成的数据
 */
public class DemoSelenium {

    static {
//        Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
    }

    public static void main(String[] args) throws Exception {
        Executor executor = new Executor() {
            @Override
            public void execute(CrawlDatum datum, CrawlDatums next) throws Exception {
                HtmlUnitDriver driver = new HtmlUnitDriver();
                driver.setJavascriptEnabled(true);
                driver.get(datum.getUrl());
                WebElement element = driver.findElement(By.className("hotcar"));
//                WebElement li = element.findElement(By.xpath("//li[@class='current']"));
//                String name = li.findElement(By.xpath("//li[@class='current']//span")).getText();
                List<WebElement> liList = driver.findElements(By.xpath("//div[@class='tab hotcar']//div[@class='tab-nav']//ul//span[@data-toggle='tab']"));
//                for (WebElement we : list) {
//                    we.submit();
//                    System.out.println(we.getText());
//                    weList.add(driver.findElement(By.id("auto-index-hotcar-box")));
//                }
//                for (WebElement we : weList) {
//                    System.out.println("-------------------");
//                    System.out.println(we.getText());
//                }
//                element = driver.findElement(By.id("auto-index-hotcar-box"));
                System.out.println(liList.size());
            }
        };
        //创建一个基于伯克利DB的DBManager
        DBManager manager = new BerkeleyDBManager("crawl");
        //创建一个Crawler需要有DBManager和Executor
        Crawler crawler = new Crawler(manager, executor);
        crawler.addSeed("http://www.autohome.com.cn/beijing/");
        crawler.start(1);
    }
}
