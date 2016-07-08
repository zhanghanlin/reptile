package com.demo.java.controller;

import com.demo.java.collector.example.CarCrawler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("fetcher")
public class FetcherController {

    @RequestMapping("/input")
    public String input() {
        return "fetcherCar";
    }

    @RequestMapping("/save")
    @ResponseBody
    public String save(String seed, String regex, int start, int thread) {
        try {
            CarCrawler.start(seed, regex, start, thread);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "OK";
    }
}
