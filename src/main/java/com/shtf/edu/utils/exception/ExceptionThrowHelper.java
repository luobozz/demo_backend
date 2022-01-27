package com.shtf.edu.utils.exception;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author chenlingyu
 */
@Component
public class ExceptionThrowHelper {


    public static void throwException(String msg) {
        throw new RuntimeException(msg);
    }

    @Async
    public void throwExceptionAsync(String msg) {
        throw new RuntimeException(msg);
    }
}
