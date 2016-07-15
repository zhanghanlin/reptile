package com.demo.java.model;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.Date;

/**
 * 抓取任务规则Bean
 */
public class Regex {
    private String id;
    private String name;    //任务名称
    private String seed;    //注入种子地址
    private String regex;   //地址正则
    private int start;  //爬取深度
    private int thread; //线程数量
    private String ignoreKey;   //抓取时如果页面存在该选择器的元素则忽略该页面
    private String data;    //自定义字段JSON格式
    private String taskKey; //任务Key唯一
    private Date createTime;
    private Date updateTime;

    /**
     * 返回JSON格式data数据
     * @return
     */
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

    /**
     * 获取过滤选择器数组
     * @return
     */
    public String[] ignoreKeys() {
        return ignoreKey.split("\\|");
    }
}
