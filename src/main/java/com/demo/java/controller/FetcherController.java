package com.demo.java.controller;

import com.demo.java.crawler.Car58LoginData;
import com.demo.java.crawler.CarCrawler;
import com.demo.java.model.Regex;
import com.demo.java.service.RegexService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("fetcher")
public class FetcherController {

    static Map<String, CarCrawler> map = new HashedMap();

    @Resource
    RegexService regexService;

    @RequestMapping("/start/{id}")
    @ResponseBody
    public void fetcherStart(@PathVariable String id) {
        Regex regex = regexService.get(id);
        try {
            CarCrawler carCrawler = new CarCrawler(regex.getTaskKey(), true);
            map.put(regex.getTaskKey(), carCrawler);
            carCrawler.start(regex);
            map.remove(regex.getTaskKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/stop/{id}")
    @ResponseBody
    public String fetcherStop(@PathVariable String id) {
        Regex regex = regexService.get(id);
        CarCrawler carCrawler = map.get(regex.getTaskKey());
        if (carCrawler == null) {
            return "没有运行的爬取任务";
        }
        carCrawler.stop();
        map.remove(regex.getTaskKey());
        return "停止爬取";
    }


    @RequestMapping("/car58")
    @ResponseBody
    public List<String> car58() {
        try {
            Car58LoginData.getCarLoginInfo("username", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Car58LoginData.titles;
    }
}
