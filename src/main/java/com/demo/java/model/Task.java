package com.demo.java.model;

import java.util.Date;

/**
 * 定时任务Bean
 */
public class Task {

    public static final String STATUS_RUNNING = "1";

    private String id;

    private Date createTime;

    private Date updateTime;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 任务分组
     */
    private String taskGroup;

    /**
     * 任务状态 是否启动任务
     */
    private String status = STATUS_RUNNING;

    /**
     * cron表达式
     */
    private String cronExpression;

    /**
     * 描述
     */
    private String description;

    /**
     * 任务执行时调用哪个类的方法 包名+类名
     */
    private String beanClass;

    /**
     * 任务调用的方法名
     */
    private String methodName;

    /**
     * 调用参数名
     */
    private String methodParam;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaskGroup() {
        return taskGroup;
    }

    public void setTaskGroup(String taskGroup) {
        this.taskGroup = taskGroup;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodParam() {
        return methodParam;
    }

    public void setMethodParam(String methodParam) {
        this.methodParam = methodParam;
    }

    public String getTriggerKey() {
        return taskGroup + "." + name;
    }
}
