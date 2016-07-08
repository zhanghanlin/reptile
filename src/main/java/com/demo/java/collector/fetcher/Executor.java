package com.demo.java.collector.fetcher;

import com.demo.java.collector.model.CrawlDatum;
import com.demo.java.collector.model.CrawlDatums;

public interface Executor {
    void execute(CrawlDatum datum, CrawlDatums next) throws Exception;
}
