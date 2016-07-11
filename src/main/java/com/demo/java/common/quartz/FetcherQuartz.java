package com.demo.java.common.quartz;

import com.demo.java.collector.example.CarCrawler;
import com.demo.java.model.Regex;
import com.demo.java.service.RegexService;
import com.demo.java.utils.SpringContextUtil;

/**
 * 动态定时抓取
 */
public class FetcherQuartz {

    RegexService regexService = (RegexService) SpringContextUtil.getBean("regexService");

    /**
     * 根据配置的规则ID进行抓取
     * @param id
     */
    public void fetcher(String id) {
        try {
            Regex regex = regexService.get(id);
            CarCrawler.start(regex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
