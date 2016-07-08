package com.demo.java.collector.plugin.ram;

import com.demo.java.collector.crawldb.DBManager;
import com.demo.java.collector.crawldb.Generator;
import com.demo.java.collector.model.CrawlDatum;
import com.demo.java.collector.model.CrawlDatums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map.Entry;

public class RamDBManager extends DBManager {

    Logger LOG = LoggerFactory.getLogger(DBManager.class);

    public RamDB ramDB;
    public RamGenerator generator = null;

    public RamDBManager(RamDB ramDB) {
        this.ramDB = ramDB;
        this.generator = new RamGenerator(ramDB);
    }

    @Override
    public boolean isDBExists() {
        return true;
    }

    @Override
    public void clear() throws Exception {
        ramDB.crawlDB.clear();
        ramDB.fetchDB.clear();
        ramDB.linkDB.clear();
        ramDB.redirectDB.clear();
    }

    @Override
    public Generator getGenerator() {
        return generator;
    }

    @Override
    public void open() throws Exception {
    }

    @Override
    public void close() throws Exception {
    }

    @Override
    public void inject(CrawlDatum datum, boolean force) throws Exception {
        String key = datum.getKey();
        if (!force) {
            if (ramDB.crawlDB.containsKey(key)) {
                return;
            }
        }
        ramDB.crawlDB.put(key, datum);
    }

    @Override
    public void inject(CrawlDatums datums, boolean force) throws Exception {
        for (CrawlDatum datum : datums) {
            inject(datum, force);
        }
    }

    @Override
    public void merge() throws Exception {
        LOG.info("start merge");

        /*合并fetch库*/
        LOG.info("merge fetch database");
        for (Entry<String, CrawlDatum> fetchEntry : ramDB.fetchDB.entrySet()) {
            ramDB.crawlDB.put(fetchEntry.getKey(), fetchEntry.getValue());
        }

        /*合并link库*/
        LOG.info("merge link database");
        for (String key : ramDB.linkDB.keySet()) {
            if (!ramDB.crawlDB.containsKey(key)) {
                ramDB.crawlDB.put(key, ramDB.linkDB.get(key));
            }
        }

        LOG.info("end merge");

        ramDB.fetchDB.clear();
        LOG.debug("remove fetch database");
        ramDB.linkDB.clear();
        LOG.debug("remove link database");
    }

    @Override
    public void initSegmentWriter() throws Exception {
    }

    @Override
    public void writeFetchSegment(CrawlDatum fetchDatum) throws Exception {
        ramDB.fetchDB.put(fetchDatum.getKey(), fetchDatum);
    }

    @Override
    public void writeParseSegment(CrawlDatums parseDatums) throws Exception {
        for (CrawlDatum datum : parseDatums) {
            ramDB.linkDB.put(datum.getKey(), datum);
        }
    }

    @Override
    public void closeSegmentWriter() throws Exception {
    }

    @Override
    public void lock() throws Exception {
    }

    @Override
    public boolean isLocked() throws Exception {
        return false;
    }

    @Override
    public void unlock() throws Exception {
    }

    @Override
    public void writeRedirectSegment(CrawlDatum datum, String realUrl) throws Exception {
        String key = datum.getKey();
        ramDB.redirectDB.put(key, realUrl);
    }
}
