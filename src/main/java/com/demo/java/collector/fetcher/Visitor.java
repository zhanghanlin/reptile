package com.demo.java.collector.fetcher;

import com.demo.java.collector.model.CrawlDatums;
import com.demo.java.collector.model.Page;

public interface Visitor {
    void visit(Page page, CrawlDatums next);
}
