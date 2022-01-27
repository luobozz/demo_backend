package com.shtf.edu.shiro;

import org.apache.shiro.authc.AuthenticationToken;

import java.util.Map;

/**
 * ShiroToken class
 *
 * @author chenlingyu
 * @date 2021/5/5 0:01
 */
public class ShiroToken implements AuthenticationToken {
    private Map<String,String> principalMap;

    public ShiroToken(Map<String,String> principalMap) {
        this.principalMap = principalMap;
    }

    @Override
    public Object getPrincipal() {
        return principalMap;
    }

    @Override
    public Object getCredentials() {
        return principalMap;
    }
}
