package com.demo.java.common.quartz;

import com.demo.java.model.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 定时任务工具类
 */
public class TaskUtils {

    final static Logger LOG = LoggerFactory.getLogger(TaskUtils.class);

    /**
     * 通过反射调用scheduleJob中定义的方法
     *
     * @param task
     */
    public static void invokeMethod(Task task) {
        Object object = null;
        Class clazz;
        if (StringUtils.isNotBlank(task.getBeanClass())) {
            try {
                clazz = Class.forName(task.getBeanClass());
                object = clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (object == null) {
            LOG.error("任务名称[{}]未启动成功，请检查是否配置正确！！！", task.getName());
            return;
        }
        clazz = object.getClass();
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(task.getMethodName(), String.class);
        } catch (NoSuchMethodException e) {
            LOG.error("任务名称[{}]未启动成功，方法名设置错误！！！", task.getMethodName());
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        if (method != null) {
            try {
                method.invoke(object, task.getMethodParam());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
