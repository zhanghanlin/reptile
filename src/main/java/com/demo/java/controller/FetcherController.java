package com.demo.java.controller;

import com.demo.java.crawler.Car58LoginData;
import com.demo.java.crawler.CarCrawler;
import com.demo.java.model.Regex;
import com.demo.java.service.RegexService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("fetcher")
public class FetcherController {

    @Resource
    RegexService regexService;

    @RequestMapping("/start/{id}")
    @ResponseBody
    public String fetcherStart(@PathVariable String id) {
        Regex regex = regexService.get(id);
        try {
            CarCrawler.start(regex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "后台爬取中...";
    }

    @RequestMapping("/stop/{id}")
    @ResponseBody
    public String fetcherStop(@PathVariable String id) {
        Regex regex = regexService.get(id);
        CarCrawler.stop(regex);
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
