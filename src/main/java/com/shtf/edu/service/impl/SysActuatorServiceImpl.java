package com.shtf.edu.service.impl;

import com.alibaba.fastjson.JSON;
import com.shtf.edu.bean.api.system.actuator.query.ActuatorRedisQuery;
import com.shtf.edu.service.SysActuatorService;
import com.shtf.edu.utils.responseMessage.ResponseMessage;
import com.shtf.edu.utils.responseMessage.ResponseMessageHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * @author chenlingyu
 */
@Service
public class SysActuatorServiceImpl implements SysActuatorService {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Override
    public Properties getRedisMonitor(ActuatorRedisQuery query) {
        Properties properties=redisConnectionFactory.getConnection().info();
        return properties;
    }
}
