package com.alibaba.fescar.operatelog;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author: wenyixicodedog
 * @create: 2021-01-31
 * @description:
 */
public class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtil.applicationContext = applicationContext;
    }

    //public static Object getObject(OperateLogParam operateLogParam) throws BeansException {
    //    return new Object();
    //}

}
