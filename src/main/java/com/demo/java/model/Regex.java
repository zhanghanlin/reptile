package com.demo.java.model;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringEscapeUtils;

public class Regex {
    private String id;
    private String seed;
    private String regex;
    private int start;
    private int thread;
    private String data;

    public JSONObject getJSONData() {
        return JSONObject.parseObject(StringEscapeUtils.unescapeHtml4(data));
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getThread() {
        return thread;
    }

    public void setThread(int thread) {
        this.thread = thread;
    }
}
