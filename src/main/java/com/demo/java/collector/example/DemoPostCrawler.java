package com.demo.java.collector.example;

import com.demo.java.collector.model.CrawlDatum;
import com.demo.java.collector.model.CrawlDatums;
import com.demo.java.collector.model.Page;
import com.demo.java.collector.net.HttpRequest;
import com.demo.java.collector.net.HttpResponse;
import com.demo.java.collector.plugin.berkeley.BreadthCrawler;

import org.json.JSONObject;

/**
 * 本教程演示了如何自定义http请求
 * <p>
 * 有些爬取任务中，可能只有部分URL需要使用POST请求，我们可以利用2.20版本中添 加的MetaData功能，来完成POST请求的定制。
 * <p>
 * 使用MetaData除了可以标记URL是否需要使用POST，还可以存储POST所需的参数信息
 * <p>
 * 教程中还演示了如何定制Cookie、User-Agent等http请求头信息
 * <p>
 * WebCollector中已经包含了org.json的jar包
 */
public class DemoPostCrawler extends BreadthCrawler {

    public DemoPostCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
    }

    @Override
    public HttpResponse getResponse(CrawlDatum crawlDatum) throws Exception {
        HttpRequest request = new HttpRequest(crawlDatum.getUrl());

        request.setMethod(crawlDatum.meta("method"));
        String outputData = crawlDatum.meta("outputData");
        if (outputData != null) {
            request.setOutputData(outputData.getBytes("utf-8"));
        }
        return request.getResponse();
        /*
        //通过下面方式可以设置Cookie、User-Agent等http请求头信息
        request.setCookie("xxxxxxxxxxxxxx");
        request.setUserAgent("WebCollector");
        request.addHeader("xxx", "xxxxxxxxx");
        */
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        String jsonStr = page.getHtml();
        JSONObject json = new JSONObject(jsonStr);
        System.out.println("JSON信息：" + json);
    }

    /**
     * 假设我们要爬取三个链接 1)http://www.A.com/index.php 需要POST，并且需要附带数据id=a
     * 2)http://www.B.com/index.php?id=b 需要POST，不需要附带数据 3)http://www.C.com/
     * 需要GET
     *
     * @param args 参数
     * @throws Exception 异常
     */
    public static void main(String[] args) throws Exception {
        DemoPostCrawler crawler = new DemoPostCrawler("json_crawler", true);
        crawler.addSeed(new CrawlDatum("http://www.A.com/index.php")
                .meta("method", "POST")
                .meta("outputData", "id=a"));
        crawler.addSeed(new CrawlDatum("http://www.B.com/index.php")
                .meta("method", "POST"));
        crawler.addSeed(new CrawlDatum("http://www.C.com/index.php")
                .meta("method", "GET"));
        crawler.start(1);
    }
}
