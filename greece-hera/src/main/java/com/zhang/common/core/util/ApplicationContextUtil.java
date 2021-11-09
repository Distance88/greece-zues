package com.zhang.common.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 * @author yanlv
 * @version 0.1 : ApplicationContextUtil v0.1 2016/12/6 下午8:29 yanlv Exp $
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static void autowire(Object bean) {
        applicationContext.getAutowireCapableBeanFactory().autowireBean(bean);
    }

    public static ApplicationContext getContext(){
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static Object getBean(String var1) throws BeansException {
        return applicationContext.getBean(var1);
    }

    public static <T> T getBean(String var1, Class<T> var2) throws BeansException {
        return applicationContext.getBean(var1, var2);
    }

    public static <T> T getBean(Class<T> var1) throws BeansException {
        return applicationContext.getBean(var1);
    }

    public Object getBean(String var1, Object... var2) throws BeansException {
        return applicationContext.getBean(var1, var2);
    }

    public <T> T getBean(Class<T> var1, Object... var2) throws BeansException {
        return applicationContext.getBean(var1, var2);
    }

    public static String[] getBeanDefinitionNames() {
        return applicationContext.getBeanDefinitionNames();
    }

    public static String[] getBeanNamesForType(ResolvableType var1) {
        return applicationContext.getBeanNamesForType(var1);
    }

    public static String[] getBeanNamesForType(Class<?> var1) {
        return applicationContext.getBeanNamesForType(var1);
    }

    public static String[] getBeanNamesForType(Class<?> var1, boolean var2, boolean var3) {
        return applicationContext.getBeanNamesForType(var1, var2, var3);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> var1) throws BeansException {
        return applicationContext.getBeansOfType(var1);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> var1, boolean var2, boolean var3)
                                                                                              throws BeansException {
        return applicationContext.getBeansOfType(var1, var2, var3);
    }

}
