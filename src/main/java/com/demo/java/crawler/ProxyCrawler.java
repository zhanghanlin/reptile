package com.demo.java.crawler;

import com.demo.java.collector.model.CrawlDatums;
import com.demo.java.collector.model.Page;
import com.demo.java.collector.plugin.berkeley.BreadthCrawler;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 代理抓取
 */
public class ProxyCrawler extends BreadthCrawler {

    final static Logger LOG = LoggerFactory.getLogger(ProxyCrawler.class);

    /**
     * 构造一个基于伯克利DB的爬虫
     * 伯克利DB文件夹为crawlPath，crawlPath中维护了历史URL等信息
     * 不同任务不要使用相同的crawlPath
     * 两个使用相同crawlPath的爬虫并行爬取会产生错误
     *
     * @param crawlPath 伯克利DB使用的文件夹
     * @param autoParse 是否根据设置的正则自动探测新URL
     */
    public ProxyCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        //判断地址是否符合规则
        if (!page.matchUrl(proxy_regex)) {
            return;
        }
        Document doc = page.doc();
        Elements elements = doc.select("tbody tr");
        for (Element ele : elements) {
            String ip = ele.select("td").eq(0).text();
            String port = ele.select("td").eq(1).text();
            String nmd = ele.select("td").eq(2).text();
            if (nmd.equals("高匿名")) {
                writeProxy(ip + ":" + port + "\r\n", true);
            }
        }
    }

    /**
     * 使用FileWriter
     */
    public static void writeProxy(String content, boolean append) {
        try {
            String classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            File file = new File(classPath + "/proxy");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(file, append);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String proxy_regex = "http://www.kuaidaili.com/proxylist/[0-9]+/";

    /**
     * 启动抓取任务
     *
     * @throws Exception
     */
    public static void startProxy() throws Exception {
        LOG.info("update proxy ip run");
        writeProxy("", false);  //清空代理列表
        ProxyCrawler crawler = new ProxyCrawler("proxys", true);
        crawler.addSeed("http://www.kuaidaili.com/proxylist/1/");
        crawler.addRegex(proxy_regex);
        crawler.setThreads(1);
        crawler.start(5);
        LOG.info("update proxy ip end");
    }
}
