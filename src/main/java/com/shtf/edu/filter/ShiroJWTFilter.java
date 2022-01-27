package com.shtf.edu.filter;

import com.shtf.edu.bean.api.system.account.vo.JWTSession;
import com.shtf.edu.constant.SystemConstants;
import com.shtf.edu.shiro.ShiroToken;
import com.shtf.edu.utils.encryption.JWTHelper;
import com.shtf.edu.utils.encryption.JWTSessionHelper;
import io.jsonwebtoken.Claims;
import lombok.SneakyThrows;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.security.auth.message.AuthException;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenlingyu
 */
public class ShiroJWTFilter extends BasicHttpAuthenticationFilter {


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(SystemConstants.ShiroConstants.REQUEST_HEADER_TOKEN);
        String origin = httpServletRequest.getHeader(SystemConstants.ShiroConstants.REQUEST_HEADER_ORIGIN);
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(origin)) {
            throw new AuthenticationException("非法登陆");
        }

        Map<String, String> principalMap = new HashMap<String, String>();
        principalMap.put(SystemConstants.ShiroConstants.SHIRO_PRINCIPAL_MAP_TOKEN, token);
        principalMap.put(SystemConstants.ShiroConstants.SHIRO_PRINCIPAL_MAP_ORIGIN, origin);

        ShiroToken shiroToken = new ShiroToken(principalMap);
        getSubject(request, response).login(shiroToken);
        return true;
    }

}
