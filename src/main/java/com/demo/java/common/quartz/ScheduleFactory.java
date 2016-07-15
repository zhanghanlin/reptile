package com.demo.java.common.quartz;

import com.demo.java.model.Task;
import com.demo.java.service.TaskService;
import com.demo.java.common.utils.SpringContextUtil;
import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ScheduleFactory {

    final static Logger LOG = LoggerFactory.getLogger(ScheduleFactory.class);

    // 实例化线程池
    StdScheduler scheduler = (StdScheduler) SpringContextUtil.getBean("schedulerFactoryBean");

    TaskService taskService = (TaskService) SpringContextUtil.getBean("taskService");

    /**
     * 添加任务
     *
     * @param task
     * @throws SchedulerException
     */
    public void addJob(Task task) throws SchedulerException {
        taskService.save(task);
        if (task == null || !Task.STATUS_RUNNING.equals(task.getStatus())) {
            return;
        }
        LOG.info("{} add", scheduler);
        TriggerKey triggerKey = TriggerKey.triggerKey(task.getName(), task.getTaskGroup());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        // 不存在，创建一个
        if (null == trigger) {
            JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class).withIdentity(task.getName(), task.getTaskGroup()).build();
            jobDetail.getJobDataMap().put("scheduleJob", task);
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCronExpression());
            trigger = TriggerBuilder.newTrigger().withIdentity(task.getName(), task.getTaskGroup()).withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            // Trigger已存在，那么更新相应的定时设置
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCronExpression());
            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        }
    }

    /**
     * 获取所有计划中的任务列表
     *
     * @return
     * @throws SchedulerException
     */
    public List<Task> getAllJob() throws SchedulerException {
        Map<String, Task> dbMap = taskService.map();
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        for (JobKey jobKey : jobKeys) {
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                Task task = dbMap.get(trigger.getKey().toString());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                task.setStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    task.setCronExpression(cronExpression);
                }
                dbMap.put(trigger.getKey().toString(), task);
            }
        }
        return new ArrayList<>(dbMap.values());
    }

    /**
     * 所有正在运行的job
     *
     * @return
     * @throws SchedulerException
     */
    public List<Task> getRunningJob() throws SchedulerException {
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        List<Task> jobList = new ArrayList<>(executingJobs.size());
        for (JobExecutionContext executingJob : executingJobs) {
            Task task = new Task();
            JobDetail jobDetail = executingJob.getJobDetail();
            JobKey jobKey = jobDetail.getKey();
            Trigger trigger = executingJob.getTrigger();
            task.setName(jobKey.getName());
            task.setTaskGroup(jobKey.getGroup());
            task.setDescription("触发器:" + trigger.getKey());
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            task.setStatus(triggerState.name());
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                String cronExpression = cronTrigger.getCronExpression();
                task.setCronExpression(cronExpression);
            }
            jobList.add(task);
        }
        return jobList;
    }

    /**
     * 暂停一个job
     *
     * @param task
     * @throws SchedulerException
     */
    public void pauseJob(Task task) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(task.getName(), task.getTaskGroup());
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复一个job
     *
     * @param task
     * @throws SchedulerException
     */
    public void resumeJob(Task task) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(task.getName(), task.getTaskGroup());
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除一个job
     *
     * @param task
     * @throws SchedulerException
     */
    public void deleteJob(Task task) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(task.getName(), task.getTaskGroup());
        scheduler.deleteJob(jobKey);
        taskService.delete(task.getId());
    }

    /**
     * 立即执行job
     *
     * @param task
     * @throws SchedulerException
     */
    public void runAJobNow(Task task) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(task.getName(), task.getTaskGroup());
        scheduler.triggerJob(jobKey);
    }

    /**
     * 更新job时间表达式
     *
     * @param task
     * @throws SchedulerException
     */
    public void updateJobCron(Task task) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(task.getName(), task.getTaskGroup());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCronExpression());
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        scheduler.rescheduleJob(triggerKey, trigger);
    }
}
