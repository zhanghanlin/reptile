package com.demo.java.crawler;

import com.demo.java.collector.model.CrawlDatums;
import com.demo.java.collector.model.Page;
import com.demo.java.collector.plugin.berkeley.BreadthCrawler;
import com.demo.java.collector.util.FileUtils;
import com.demo.java.common.utils.Config;
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
    public void writeProxy(String content, boolean append) {
        try {
            File file = new File(classPath + "/proxy.bak");
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

    static String classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    static String proxy_regex = "http://www.kuaidaili.com/proxylist/[0-9]+/";

    /**
     * 启动抓取任务
     *
     * @throws Exception
     */
    public void startProxy() throws Exception {
        writeProxy(Config.STRING_EMPTY, false);  //清空代理列表
        this.addSeed("http://www.kuaidaili.com/proxylist/1/");
        this.addRegex(proxy_regex);
        this.setThreads(1);
        this.start(2);
        FileUtils.copy(new File(classPath + "/proxy.bak"), new File(classPath + "/proxy"));
    }

    public static void main(String[] args) throws Exception {
        ProxyCrawler crawler = new ProxyCrawler("proxy", true);
        crawler.startProxy();
        crawler.stop();
    }
}
