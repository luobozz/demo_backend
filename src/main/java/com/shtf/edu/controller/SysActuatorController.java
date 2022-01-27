package com.shtf.edu.controller;

import com.alibaba.fastjson.JSON;
import com.shtf.edu.bean.api.system.actuator.query.ActuatorRedisQuery;
import com.shtf.edu.service.SysActuatorService;
import com.shtf.edu.utils.responseMessage.ResponseMessage;
import com.shtf.edu.utils.responseMessage.ResponseMessageHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Properties;

/**
 * ActuatorApiController
 * 用以增强Actuator功能
 * @author chenlingyu
 */
@RestController
@RequestMapping("/sys/actuator")
public class SysActuatorController {

    @Autowired
    private SysActuatorService actuatorService;
    @Autowired
    private ResponseMessageHandle responseMessageHandle;

    @GetMapping("/")
    public void application() {
    }

    @GetMapping("/redis")
    public ResponseMessage actuatorRedis(@Valid ActuatorRedisQuery query) {
        Properties properties=actuatorService.getRedisMonitor(query);
        return responseMessageHandle.code(200).data(JSON.parseObject(JSON.toJSONString(properties)));
    }

}