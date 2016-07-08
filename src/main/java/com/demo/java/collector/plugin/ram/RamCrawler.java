package com.demo.java.collector.plugin.ram;

import com.demo.java.collector.crawler.AutoParseCrawler;

/**
 * 基于内存的Crawler插件，适合一次性爬取，并不具有断点爬取功能
 * 长期任务请使用BreadthCrawler
 */
public abstract class RamCrawler extends AutoParseCrawler {

    public RamCrawler() {
        this(true);
    }

    public RamCrawler(boolean autoParse) {
        super(autoParse);
        RamDB ramDB = new RamDB();
        this.dbManager = new RamDBManager(ramDB);
    }

    public void start() throws Exception {
        start(Integer.MAX_VALUE);
    }
}
