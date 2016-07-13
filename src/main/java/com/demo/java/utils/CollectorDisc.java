package com.demo.java.utils;

import org.apache.commons.collections.map.HashedMap;

import java.util.Map;

public class CollectorDisc {

    public static Map<String, String> map = new HashedMap();

    static {
        map.put("carName","名称");
        map.put("price","价格");
        map.put("onTime","上牌时间");
        map.put("mileage","里程数");
        map.put("speedCase","变速箱类型");
        map.put("inspectExpire","年检到期");
        map.put("safeExpire","保险到期");
        map.put("accident","事故");
        map.put("userName","联系人");
        map.put("phone","联系方式");
    }
}
