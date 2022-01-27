package com.shtf.edu.utils.spring;

import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author zhangjiadong
 * @date 2020/6/11
 */
@Component
public class TaskAsyncHelper {

    public static <T> void doTask(Class clazz, String name, T... params) {
        Class[] classes = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
            classes[i] = params[i].getClass();
        }
        try {
            Method method = clazz.getDeclaredMethod(name, classes);
            method.invoke(SpringContextHelper.getApplicationContext().getBean(clazz), params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
