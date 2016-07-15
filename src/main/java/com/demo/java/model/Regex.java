package com.demo.java.model;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.Date;

public class Regex {
    private String id;
    private String name;
    private String seed;
    private String regex;
    private int start;
    private int thread;
    private String ignoreKey;
    private String data;
    private String taskKey;
    private Date createTime;
    private Date updateTime;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getIgnoreKey() {
        return ignoreKey;
    }

    public String getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public void setIgnoreKey(String ignoreKey) {
        this.ignoreKey = ignoreKey;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String[] ignoreKeys() {
        return ignoreKey.split("\\|");
    }
}
