package com.shtf.edu.utils.basis;

import com.alibaba.fastjson.JSON;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;

/**
 * BeanHelper class
 *
 * @author chenlingyu
 * @date 2021/5/4 21:37
 */
public class BeanHelper {

    public static void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }
}
