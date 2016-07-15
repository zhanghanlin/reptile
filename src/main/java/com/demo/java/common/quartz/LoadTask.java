package com.demo.java.common.quartz;

import com.demo.java.common.utils.SpringContextUtil;
import com.demo.java.model.Task;
import com.demo.java.service.TaskService;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LoadTask {

    final static Logger LOG = LoggerFactory.getLogger(LoadTask.class);

    TaskService taskService = (TaskService) SpringContextUtil.getBean("taskService");

    public void initTask() throws SchedulerException {
        List<Task> list = taskService.list();
        if (list == null || list.isEmpty()) return;
        LOG.info("LoadTask initTask Task Size : {}", list.size());
        ScheduleFactory scheduleFactory = new ScheduleFactory();
        for (Task task : list) {
            LOG.info("LoadTask initTask createJob {}", task.getTriggerKey());
            scheduleFactory.createJob(task);
        }
    }
}
