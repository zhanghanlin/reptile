package com.demo.java.collector.net;

import com.demo.java.collector.model.CrawlDatum;

public interface Requester {
    HttpResponse getResponse(CrawlDatum crawlDatum) throws Exception;
}
