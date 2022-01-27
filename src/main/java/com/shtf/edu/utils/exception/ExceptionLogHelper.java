package com.shtf.edu.utils.exception;

import com.shtf.edu.bean.annotations.TaskPath;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @author chenlingyu
 */
@Slf4j
public class ExceptionLogHelper {


    public static void exceptionLog(Class exceptionClz,String taskInfo, StackTraceElement[] elements, String message) {
        exceptionGather(exceptionClz, taskInfo, elements[0].toString(), message);
    }

    public static void taskExceptionLog(Class exceptionClz, StackTraceElement[] elements, String message) {
        exceptionGather(exceptionClz, getLogMainTaskInfo(elements), elements[0].toString(), message);
    }

    private static void exceptionGather(Class exceptionClz,String taskInfo,String stackTop,String message){
        log.error("\n程序发生[{}]异常\n执行事务[{}]\n异常堆栈顶类[{}]\n异常信息[{}]", exceptionClz, taskInfo, stackTop, message);
    }

    private static String getLogMainTaskInfo(StackTraceElement[] elements) {
        String logMainTaskInfo = "";
        for (StackTraceElement element : elements) {
            try {
                Class<?> clz = Class.forName(element.getClassName());
                Method[] methods = clz.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.getName().equals(element.getMethodName()) && method.isAnnotationPresent(TaskPath.class)) {
                        logMainTaskInfo = method.getAnnotation(TaskPath.class).info() + (logMainTaskInfo == "" ? "" : ">") + logMainTaskInfo;
                    }
                }

            } catch (Exception ex) {
            }
        }
        return logMainTaskInfo == "" ? "未使用LogMainTask()" : logMainTaskInfo;
    }
}
