package com.demo.java.controller;

import com.demo.java.collector.example.Car58LoginData;
import com.demo.java.collector.example.CarCrawler;
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

    @RequestMapping("/{id}")
    @ResponseBody
    public String save(@PathVariable String id) {
        Regex regex = regexService.get(id);
        FetcherThread ft = new FetcherThread(regex);
        ft.start();
        return "后台爬取中...";
    }

    @RequestMapping("/car58")
    @ResponseBody
    public List<String> car58(){
        try {
            Car58LoginData.getCarLoginInfo("username","password");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Car58LoginData.titles;
    }

    class FetcherThread extends Thread {

        private Regex regex;

        public FetcherThread(Regex regex) {
            super();
            this.regex = regex;
        }

        @Override
        public void run() {
            try {
                CarCrawler.start(regex);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
