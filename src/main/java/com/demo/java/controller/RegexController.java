package com.demo.java.controller;

import com.demo.java.model.Regex;
import com.demo.java.service.RegexService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("regex")
public class RegexController {

    @Resource
    RegexService regexService;

    @RequestMapping("/input")
    public String input() {
        return "regexInput";
    }


    @RequestMapping("/save")
    public String save(Regex regex) {
        try {
            regex.setId(UUID.randomUUID().toString().replace("-", ""));
            regexService.save(regex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "regexList";
    }

    @RequestMapping("/list")
    public ModelAndView list() {
        List<Regex> list = regexService.list();
        return new ModelAndView("regexList", "list", list);
    }
}
