package com.demo.java.collector.example;

import com.demo.java.collector.crawldb.DBManager;
import com.demo.java.collector.crawler.Crawler;
import com.demo.java.collector.fetcher.Executor;
import com.demo.java.collector.model.CrawlDatum;
import com.demo.java.collector.model.CrawlDatums;
import com.demo.java.collector.plugin.berkeley.BerkeleyDBManager;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.Jdk14Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * 抓取58登陆后信息
 */
public class Car58LoginData {

    static {
        ((Jdk14Logger) LogFactory.getLog("com.gargoylesoftware.htmlunit")).getLogger().setLevel(Level.OFF);
    }

    public static List<String> titles = new ArrayList<>();
    private static String userName;
    private static String password;

    public static void getCarInfo() throws Exception {
        Executor executor = new Executor() {
            @Override
            public void execute(CrawlDatum datum, CrawlDatums next) throws Exception {
                HtmlUnitDriver driver = new HtmlUnitDriver(true);
                driver.get(datum.getUrl());
                WebElement loginTab = driver.findElement(By.id("login_tab_orig"));
                loginTab.click();
                WebElement formEle = driver.findElement(By.id("submitForm_new"));
                WebElement usernameEle = driver.findElement(By.id("username_new"));
                WebElement passwordEle = driver.findElement(By.id("password_new"));
                usernameEle.sendKeys(userName);
                passwordEle.sendKeys(password);
                formEle.submit();
                WebElement iFrame = driver.findElement(By.id("ContainerFrame"));
                String src = iFrame.getAttribute("src");
                WebDriver.Navigation navigation = driver.navigate();
                navigation.to(src);
                List<WebElement> tr = driver.findElements(By.xpath("//div[@class='my-item-item']"));
                for (WebElement we : tr) {
                    String title = we.findElement(By.className("my-item-item-content-left-title"))
                            .findElement(By.tagName("span")).getText();
                    titles.add(title);
                }
//                WebElement li = element.findElement(By.xpath("//li[@class='current']"));
//                String name = li.findElement(By.xpath("//li[@class='current']//span")).getText();
//                List<WebElement> liList = driver.findElements(By.xpath("//div[@class='tab hotcar']//div[@class='tab-nav']//ul//span[@data-toggle='tab']"));
            }
        };
        //创建一个基于伯克利DB的DBManager
        DBManager manager = new BerkeleyDBManager("crawl");
        //创建一个Crawler需要有DBManager和Executor
        Crawler crawler = new Crawler(manager, executor);
        crawler.addSeed("http://my.58.com/pro/infoall");
        crawler.start(1);
    }

    public static void getCarLoginInfo(String uName, String pWord) throws Exception {
        userName = uName;
        password = pWord;
        getCarInfo();
    }

    public static void main(String[] args) throws Exception {
        getCarInfo();
    }
}
