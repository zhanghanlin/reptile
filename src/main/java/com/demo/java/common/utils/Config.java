package com.demo.java.common.utils;

/**
 * 全局配置
 */
public interface Config {

    /**
     * String Empty Val
     */
    String STRING_EMPTY = "";

    String DEFAULT_ENCODE = "UTF-8";

    /**
     * 代理IP存放文件
     */
    String PROXY_FILE = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "/proxy";
    String PROXY_FILE_BAK = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "/proxy_bak";
}
