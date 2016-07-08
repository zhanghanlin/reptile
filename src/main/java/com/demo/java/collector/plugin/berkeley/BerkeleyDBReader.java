package com.demo.java.collector.plugin.berkeley;

import com.demo.java.collector.model.CrawlDatum;
import com.sleepycat.je.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class BerkeleyDBReader {

    public static final Logger LOG = LoggerFactory.getLogger(BerkeleyDBReader.class);
    public String crawlPath;

    Cursor cursor = null;
    Database crawldbDatabase = null;
    Environment env = null;

    public BerkeleyDBReader(String crawlPath) {
        this.crawlPath = crawlPath;
        File dir = new File(crawlPath);
        EnvironmentConfig environmentConfig = new EnvironmentConfig();
        environmentConfig.setAllowCreate(true);
        env = new Environment(dir, environmentConfig);
        crawldbDatabase = env.openDatabase(null, "crawldb", BerkeleyDBUtils.defaultDBConfig);
        cursor = crawldbDatabase.openCursor(null, CursorConfig.DEFAULT);
    }

    protected DatabaseEntry key = new DatabaseEntry();
    protected DatabaseEntry value = new DatabaseEntry();

    public CrawlDatum next() throws Exception {
        if (cursor.getNext(key, value, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
            CrawlDatum datum = BerkeleyDBUtils.createCrawlDatum(key, value);
            return datum;
        } else {
            return null;
        }
    }

    public void close() {
        if (cursor != null) {
            cursor.close();
        }
        cursor = null;
        if (crawldbDatabase != null) {
            crawldbDatabase.close();
        }
        if (env != null) {
            env.close();
        }
    }
}
