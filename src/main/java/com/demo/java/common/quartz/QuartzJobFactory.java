package com.demo.java.common.quartz;

import com.demo.java.model.Task;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 计划任务执行
 */
public class QuartzJobFactory implements Job {

    final static Logger LOG = LoggerFactory.getLogger(QuartzJobFactory.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOG.info("Task Run");
        Task task = (Task) context.getMergedJobDataMap().get("scheduleJob");
        LOG.info("Task Name:" + task.getName());
        TaskUtils.invokeMethod(task);
    }
}
