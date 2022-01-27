package com.shtf.edu.controller;

import com.shtf.edu.mapper.SysRoleRouteMapper;
import com.shtf.edu.utils.responseMessage.ResponseMessage;
import com.shtf.edu.utils.responseMessage.ResponseMessageHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenlingyu
 */
@RestController
@RequestMapping("/sys/account/")
public class SysAccountController {
    @Autowired
    private SysRoleRouteMapper sysRoleRouteMapper;
    @Autowired
    private ResponseMessageHandle responseMessageHandle;

    @GetMapping("login")
    public ResponseMessage login() {
        return null;
    }

    @GetMapping("logout")
    public ResponseMessage logout() {
        return null;
    }
}
