package com.demo.java.common.vo;

import java.io.Serializable;
import java.util.List;

/**
 * JQuery DataTable 使用的PageVo
 * @param <T>
 */
public class TablePage<T> implements Serializable {

    private static final long serialVersionUID = 2947666872800248033L;

    private int draw; // 来自客户端 sEcho 的没有变化的复制品

    private long recordsTotal; // 实际的行数

    private long recordsFiltered; // 过滤之后实际的行数。

    private List<T> data;

    public TablePage(int draw, long recordsTotal, long recordsFiltered, List<T> data) {
        super();
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.data = data;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}