package com.demo.java.controller;

import com.demo.java.common.quartz.ScheduleFactory;
import com.demo.java.common.quartz.ScheduleJob;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("quartz")
public class QuartzController {

    ScheduleFactory scheduleFactory = new ScheduleFactory();

    @RequestMapping("input")
    public ModelAndView input(@RequestParam(defaultValue = "") String regexId) {
        return new ModelAndView("quartzInput", "regexId", regexId);
    }

    @RequestMapping("add")
    @ResponseBody
    public String add(ScheduleJob job) {
        try {
            job.setUpdateTime(new Date());
            job.setCreateTime(new Date());
            scheduleFactory.addJob(job);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "OK";
    }

    @RequestMapping("list")
    public ModelAndView list() {
        List<ScheduleJob> list = new ArrayList<>();
        try {
            list = scheduleFactory.getAllJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return new ModelAndView("quartzList", "list", list);
    }

    @RequestMapping("pause/{name}/{group}")
    @ResponseBody
    public String pauseJob(@PathVariable String name, @PathVariable String group) {
        try {
            ScheduleJob job = new ScheduleJob();
            job.setName(name);
            job.setGroup(group);
            scheduleFactory.pauseJob(job);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "OK";
    }


    @RequestMapping("resume/{name}/{group}")
    @ResponseBody
    public String resumeJob(@PathVariable String name, @PathVariable String group) {
        try {
            ScheduleJob job = new ScheduleJob();
            job.setName(name);
            job.setGroup(group);
            scheduleFactory.resumeJob(job);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "OK";
    }

    @RequestMapping("delete/{name}/{group}")
    @ResponseBody
    public String deleteJob(@PathVariable String name, @PathVariable String group) {
        try {
            ScheduleJob job = new ScheduleJob();
            job.setName(name);
            job.setGroup(group);
            scheduleFactory.deleteJob(job);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "OK";
    }
}
