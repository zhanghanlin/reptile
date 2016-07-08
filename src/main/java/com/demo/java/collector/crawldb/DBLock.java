package com.demo.java.collector.crawldb;

public interface DBLock {

    void lock() throws Exception;

    boolean isLocked() throws Exception;

    void unlock() throws Exception;
}
