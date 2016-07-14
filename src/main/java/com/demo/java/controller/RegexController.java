package com.demo.java.controller;

import com.alibaba.fastjson.JSONObject;
import com.demo.java.model.Car;
import com.demo.java.model.Regex;
import com.demo.java.service.RegexService;
import com.demo.java.utils.CollectorDisc;
import com.demo.java.utils.ReflectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("regex")
public class RegexController {

    @Resource
    RegexService regexService;

    @RequestMapping("/input/{id}")
    public ModelAndView input(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("regexInput");
        Regex regex = new Regex();
        JSONObject data = new JSONObject();
        if (!id.equals("0")) {
            regex = regexService.get(id);
            data = regex.getJSONData();
        } else {
            List<String> list = ReflectUtils.getFields(Car.class);
            list.remove("id");
            list.remove("createTime");
            list.remove("updateTime");
            list.remove("url");
            for (String s : list) {
                data.put(s, null);
            }
        }
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            JSONObject object = (JSONObject) entry.getValue();
            if (object == null) object = new JSONObject();
            object.put("name", CollectorDisc.map.get(entry.getKey()));
            data.put(entry.getKey(), object);
        }
        modelAndView.addObject("regex", regex);
        modelAndView.addObject("data", data);
        return modelAndView;
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        try {
            regexService.remove(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/regex/list";
    }

    @RequestMapping("/save")
    public String save(Regex regex) {
        try {
            if (StringUtils.isBlank(regex.getId())) {
                regex.setId(UUID.randomUUID().toString().replace("-", ""));
                regexService.save(regex);
            } else {
                regexService.update(regex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/regex/list";
    }

    @RequestMapping("/list")
    public ModelAndView list() {
        List<Regex> list = regexService.list();
        ModelAndView modelAndView = new ModelAndView("regexList");
        modelAndView.addObject("list", list);
        return modelAndView;
    }
}
