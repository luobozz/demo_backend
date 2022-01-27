package com.shtf.edu.service;

import com.shtf.edu.bean.api.system.actuator.query.ActuatorRedisQuery;
import com.shtf.edu.utils.responseMessage.ResponseMessage;

import java.util.Properties;

/**
 * @author chenlingyu
 */
public interface SysActuatorService {

    Properties getRedisMonitor(ActuatorRedisQuery query);

}
