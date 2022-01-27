package com.shtf.edu.utils.mybatis;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * UUIDhelper class
 *
 * @author chenlingyu
 * @date 2021/5/4 22:55
 */
public class UUIDHelper {
    public static String getUuid(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static List<String> getSomeUuid(int num){
        List<String> ret=new ArrayList<>();
        for(int i=0;i<num;i++){
            ret.add(getUuid());
        }
        return ret;
    }
}
