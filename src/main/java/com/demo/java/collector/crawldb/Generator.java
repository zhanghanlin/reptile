package com.demo.java.collector.crawldb;

import com.demo.java.collector.model.CrawlDatum;

/**
 * 抓取任务生成器
 */
public interface Generator {

    CrawlDatum next();

    void open() throws Exception;

    void setTopN(int topN);

    void setMaxExecuteCount(int maxExecuteCount);

    int getTotalGenerate();

    void close() throws Exception;

}
