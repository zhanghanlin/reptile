package com.demo.java.collector.plugin.ram;

import com.demo.java.collector.crawldb.Generator;
import com.demo.java.collector.model.CrawlDatum;
import com.demo.java.collector.util.Config;

import java.util.Iterator;
import java.util.Map.Entry;

public class RamGenerator implements Generator {

    RamDB ramDB;
    protected int topN = -1;
    protected int maxExecuteCount = Config.MAX_EXECUTE_COUNT;

    public RamGenerator(RamDB ramDB) {
        this.ramDB = ramDB;
    }

    public int totalGenerate = 0;
    Iterator<Entry<String, CrawlDatum>> iterator;

    @Override
    public CrawlDatum next() {
        if (topN >= 0) {
            if (totalGenerate >= topN) {
                return null;
            }
        }

        while (true) {
            if (iterator.hasNext()) {
                CrawlDatum datum = iterator.next().getValue();
                if (datum.getStatus() == CrawlDatum.STATUS_DB_SUCCESS) {
                    continue;
                } else if (datum.getExecuteCount() > maxExecuteCount) {
                    continue;
                } else {
                    totalGenerate++;
                    return datum;
                }
            } else {
                return null;
            }
        }
    }

    @Override
    public void open() throws Exception {
        totalGenerate = 0;
        iterator = ramDB.crawlDB.entrySet().iterator();
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

    @Override
    public int getTotalGenerate() {
        return totalGenerate;
    }

    @Override
    public void close() throws Exception {

    }
}
