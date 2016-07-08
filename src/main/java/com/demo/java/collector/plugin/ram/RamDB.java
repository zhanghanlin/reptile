package com.demo.java.collector.plugin.ram;

import com.demo.java.collector.model.CrawlDatum;

import java.util.concurrent.ConcurrentHashMap;

public class RamDB {

    protected ConcurrentHashMap<String, CrawlDatum> crawlDB = new ConcurrentHashMap<String, CrawlDatum>();
    protected ConcurrentHashMap<String, CrawlDatum> fetchDB = new ConcurrentHashMap<String, CrawlDatum>();
    protected ConcurrentHashMap<String, CrawlDatum> linkDB = new ConcurrentHashMap<String, CrawlDatum>();
    protected ConcurrentHashMap<String, String> redirectDB = new ConcurrentHashMap<String, String>();
}
