package com.shtf.edu.bean.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TaskPath {
    public String info() default "";
}
