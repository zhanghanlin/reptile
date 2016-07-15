package com.demo.java.controller;

import com.demo.java.common.quartz.FetcherQuartz;
import com.demo.java.common.quartz.ScheduleFactory;
import com.demo.java.common.utils.ReflectUtils;
import com.demo.java.model.Task;
import com.demo.java.service.TaskService;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
    TaskService jobService;

    @RequestMapping("input/{regexId}")
    public ModelAndView input(@PathVariable String regexId) {
        ModelAndView modelAndView = new ModelAndView("quartzInput");
        modelAndView.addObject("regexId", regexId);
        modelAndView.addObject("methods", ReflectUtils.getMethod(FetcherQuartz.class));
        return modelAndView;
    }

    @RequestMapping("save")
    public String save(Task job) {
        try {
            job.setUpdateTime(new Date());
            job.setCreateTime(new Date());
            scheduleFactory.addJob(job);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "redirect:/quartz/list";
    }

    @RequestMapping("list")
    public ModelAndView list() {
        List<Task> list = new ArrayList<>();
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
            Task job = jobService.get(id);
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
            Task job = jobService.get(id);
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
            Task job = jobService.get(id);
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
            Task job = jobService.get(id);
            scheduleFactory.runAJobNow(job);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "OK";
    }
}
