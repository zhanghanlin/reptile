package com.demo.java.collector.crawldb;

import com.demo.java.collector.model.CrawlDatum;

public interface Injector {
    void inject(CrawlDatum datum) throws Exception;
}
