package com.demo.java.collector.plugin.berkeley;

import com.demo.java.collector.crawler.AutoParseCrawler;

/**
 * BreadthCrawler是基于Berkeley DB的插件,于2.20版重新设计
 * BreadthCrawler可以设置正则规律，让遍历器自动根据URL的正则遍历网站，可以关闭这个功能，自定义遍历
 * 如果autoParse设置为true，遍历器会自动解析页面中符合正则的链接，加入后续爬取任务，否则不自动解析链接。
 * 注意，爬虫会保证爬取任务的唯一性，也就是会自动根据CrawlDatum的key进行去重，默认情况下key就是URL，
 * 所以用户在编写爬虫时完全不必考虑生成重复URL的问题。
 * 断点爬取中，爬虫仍然会保证爬取任务的唯一性。
 *
 * @author hu
 */
public abstract class BreadthCrawler extends AutoParseCrawler {

    /**
     * 构造一个基于伯克利DB的爬虫
     * 伯克利DB文件夹为crawlPath，crawlPath中维护了历史URL等信息
     * 不同任务不要使用相同的crawlPath
     * 两个使用相同crawlPath的爬虫并行爬取会产生错误
     *
     * @param crawlPath 伯克利DB使用的文件夹
     * @param autoParse 是否根据设置的正则自动探测新URL
     */
    public BreadthCrawler(String crawlPath, boolean autoParse) {
        super(autoParse);
        this.dbManager = new BerkeleyDBManager(crawlPath);
    }
}
