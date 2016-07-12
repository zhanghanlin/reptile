package com.demo.java.controller;

import com.demo.java.common.quartz.ScheduleFactory;
import com.demo.java.common.quartz.ScheduleJob;
import com.demo.java.service.JobService;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("quartz")
public class QuartzController {

    ScheduleFactory scheduleFactory = new ScheduleFactory();

    @Resource
    JobService jobService;

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

    @RequestMapping("pause/{id}")
    @ResponseBody
    public String pauseJob(@PathVariable String id) {
        try {
            ScheduleJob job = jobService.get(id);
            scheduleFactory.pauseJob(job);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "OK";
    }


    @RequestMapping("resume/{id}")
    @ResponseBody
    public String resumeJob(@PathVariable String id) {
        try {
            ScheduleJob job = jobService.get(id);
            scheduleFactory.resumeJob(job);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "OK";
    }

    @RequestMapping("delete/{id}")
    @ResponseBody
    public String deleteJob(@PathVariable String id) {
        try {
            ScheduleJob job = jobService.get(id);
            scheduleFactory.deleteJob(job);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "OK";
    }


    @RequestMapping("run/{id}")
    @ResponseBody
    public String runJob(@PathVariable String id) {
        try {
            ScheduleJob job = jobService.get(id);
            scheduleFactory.runAJobNow(job);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "OK";
    }
}
