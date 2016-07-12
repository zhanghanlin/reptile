package com.demo.java.controller;

import com.demo.java.model.Car;
import com.demo.java.model.Regex;
import com.demo.java.service.RegexService;
import com.demo.java.utils.ReflectUtils;
import org.apache.commons.lang3.StringEscapeUtils;
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
    public ModelAndView input() {
        List<String> list = ReflectUtils.getFields(Car.class);
        list.remove("id");  //id为根据URL获取
        ModelAndView modelAndView = new ModelAndView("regexInput");
        modelAndView.addObject("list", list);
        return modelAndView;
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
        ModelAndView modelAndView = new ModelAndView("regexList");
        modelAndView.addObject("list", list);
        return modelAndView;
    }

    public static void main(String[] args) {
        System.out.println(StringEscapeUtils.unescapeHtml4("{&quot;kilometre&quot;:{&quot;index&quot;:&quot;2&quot;,&quot;select&quot;:&quot;#content_sumary_right h2&quot;,&quot;split&quot;:&quot;|&quot;,&quot;type&quot;:&quot;2&quot;},&quot;litre&quot;:{&quot;index&quot;:&quot;3&quot;,&quot;select&quot;:&quot;#content_sumary_right h2&quot;,&quot;split&quot;:&quot;|&quot;,&quot;type&quot;:&quot;2&quot;},&quot;title&quot;:{&quot;index&quot;:&quot;&quot;,&quot;select&quot;:&quot;#content_sumary_right h1&quot;,&quot;split&quot;:&quot;&quot;,&quot;type&quot;:&quot;1&quot;},&quot;price&quot;:{&quot;index&quot;:&quot;&quot;,&quot;select&quot;:&quot;.font_jiage&quot;,&quot;split&quot;:&quot;&quot;,&quot;type&quot;:&quot;1&quot;},&quot;year&quot;:{&quot;index&quot;:&quot;1&quot;,&quot;select&quot;:&quot;#content_sumary_right h2&quot;,&quot;split&quot;:&quot;|&quot;,&quot;type&quot;:&quot;2&quot;},&quot;type&quot;:{&quot;index&quot;:&quot;4&quot;,&quot;select&quot;:&quot;#content_sumary_right h2&quot;,&quot;split&quot;:&quot;|&quot;,&quot;type&quot;:&quot;2&quot;}}"));
    }
}
