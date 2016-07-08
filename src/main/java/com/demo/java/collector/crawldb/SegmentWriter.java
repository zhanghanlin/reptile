package com.demo.java.collector.crawldb;

import com.demo.java.collector.model.CrawlDatum;
import com.demo.java.collector.model.CrawlDatums;

/**
 * 爬取过程中，写入爬取历史、网页Content、解析信息的Writer
 */
public interface SegmentWriter {

    void initSegmentWriter() throws Exception;

    void writeFetchSegment(CrawlDatum fetchDatum) throws Exception;

    void writeRedirectSegment(CrawlDatum datum, String realUrl) throws Exception;

    void writeParseSegment(CrawlDatums parseDatums) throws Exception;

    void closeSegmentWriter() throws Exception;
}
