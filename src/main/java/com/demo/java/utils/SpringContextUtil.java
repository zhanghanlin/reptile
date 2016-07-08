package com.demo.java.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取spring配置的bean
     * SpringContextUtil.getBean()
     *
     * @param beanName bean名称
     * @return
     */
    public static Object getBean(String beanName) {
        if (getApplicationContext() == null)
            return null;
        return getApplicationContext().getBean(beanName);
    }
}