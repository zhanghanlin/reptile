package com.demo.java.collector.plugin.berkeley;

import com.demo.java.collector.crawldb.Generator;
import com.demo.java.collector.model.CrawlDatum;
import com.demo.java.collector.util.Config;
import com.sleepycat.je.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class BerkeleyGenerator implements Generator {

    public static final Logger LOG = LoggerFactory.getLogger(BerkeleyGenerator.class);

    Cursor cursor = null;
    Database crawldbDatabase = null;
    Environment env = null;
    protected int totalGenerate = 0;
    protected int topN = -1;
    protected int maxExecuteCount = Config.MAX_EXECUTE_COUNT;
    String crawlPath;

    public BerkeleyGenerator(String crawlPath) {
        this.crawlPath = crawlPath;
    }

    @Override
    public void open() throws Exception {
        File dir = new File(crawlPath);
        EnvironmentConfig environmentConfig = new EnvironmentConfig();
        environmentConfig.setAllowCreate(true);
        env = new Environment(dir, environmentConfig);
        totalGenerate = 0;
    }

    public void close() throws Exception {
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

    protected DatabaseEntry key = new DatabaseEntry();
    protected DatabaseEntry value = new DatabaseEntry();

    @Override
    public CrawlDatum next() {
        if (topN >= 0) {
            if (totalGenerate >= topN) {
                return null;
            }
        }
        if (cursor == null) {
            crawldbDatabase = env.openDatabase(null, "crawldb", BerkeleyDBUtils.defaultDBConfig);
            cursor = crawldbDatabase.openCursor(null, CursorConfig.DEFAULT);
        }
        while (true) {
            if (cursor.getNext(key, value, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
                try {
                    CrawlDatum datum = BerkeleyDBUtils.createCrawlDatum(key, value);
                    if (datum.getStatus() == CrawlDatum.STATUS_DB_SUCCESS) {
                        continue;
                    } else {
                        if (datum.getExecuteCount() > maxExecuteCount) {
                            continue;
                        }
                        totalGenerate++;
                        return datum;
                    }
                } catch (Exception ex) {
                    LOG.error("Exception when generating", ex);
                    continue;
                }
            } else {
                return null;
            }
        }
    }

    @Override
    public int getTotalGenerate() {
        return totalGenerate;
    }

    public int getTopN() {
        return topN;
    }

    @Override
    public void setTopN(int topN) {
        this.topN = topN;
    }

    public int getMaxExecuteCount() {
        return maxExecuteCount;
    }

    @Override
    public void setMaxExecuteCount(int maxExecuteCount) {
        this.maxExecuteCount = maxExecuteCount;
    }
}
