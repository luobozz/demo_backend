package com.shtf.edu.shiro;

import com.alibaba.fastjson.JSON;
import com.shtf.edu.bean.api.system.account.vo.JWTSession;
import com.shtf.edu.bean.api.system.account.vo.JWTSessionAccount;
import com.shtf.edu.bean.api.system.account.vo.RoleRoutePermission;
import com.shtf.edu.bean.api.system.account.vo.RoutePermission;
import com.shtf.edu.bean.entity.SysPermission;
import com.shtf.edu.constant.SystemConstants;
import com.shtf.edu.service.SysAccountService;
import com.shtf.edu.utils.encryption.JWTHelper;
import com.shtf.edu.utils.encryption.JWTSessionHelper;
import io.jsonwebtoken.Claims;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenlingyu
 */
@Component
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysAccountService sysAccountService;

    /**
     * 解决 UnsupportedTokenException
     *
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof ShiroToken;
    }

    @SneakyThrows
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        Map<String, String> principalMap = (Map<String, String>) authenticationToken.getCredentials();
        String token = principalMap.get(SystemConstants.ShiroConstants.SHIRO_PRINCIPAL_MAP_TOKEN);
        //尝试解读token
        Claims jwtClaims = JWTHelper.getClaimsFromJwt(token);
        //尝试读取服务器session
        JWTSession jwtSession = JWTSessionHelper.getJwtSession(token, jwtClaims);
        //尝试刷新session(保持登陆)
        JWTSessionHelper.refreshJwtSession(token, jwtSession);
        //获取sessionAccount
        JWTSessionAccount jwtSessionAccount = JWTSessionHelper.getJwtSessionAccount(jwtSession, token);

        principalMap.put(SystemConstants.ShiroConstants.SHIRO_PRINCIPAL_MAP_SESSION_ACCOUNT, JSON.toJSONString(jwtSessionAccount));

        SimpleAuthenticationInfo simpleAuthorizationInfo = new SimpleAuthenticationInfo(principalMap, authenticationToken.getCredentials(), getName());
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Assert.isNull(principalCollection, "shiro权限获取程序错误");
        Map<String, String> principalMap = (Map<String, String>) principalCollection.getPrimaryPrincipal();

        JWTSessionAccount jwtSessionAccount=JSON.parseObject(principalMap.get(SystemConstants.ShiroConstants.SHIRO_PRINCIPAL_MAP_SESSION_ACCOUNT),JWTSessionAccount.class);
        String origin = principalMap.get(SystemConstants.ShiroConstants.SHIRO_PRINCIPAL_MAP_ORIGIN);

        List<RoleRoutePermission> roleRoutePermissions = sysAccountService.getRoleRotePermissionByRoleUuIds(jwtSessionAccount.getRoleUuIds());

        List<String> roleSigns= roleRoutePermissions.stream().map(p -> p.getSign()).collect(Collectors.toList());
        List<String> permissionSigns=getPerMissions(roleRoutePermissions,origin);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roleSet = new HashSet<>(roleSigns);
        Set<String> permissionSet = new HashSet<>(permissionSigns);
        simpleAuthorizationInfo.setRoles(roleSet);
        simpleAuthorizationInfo.addStringPermissions(permissionSet);
        return simpleAuthorizationInfo;
    }


    /**
     * 获取所有来源路由的权限，不去重
     * @param roleRoutePermissions
     * @param origin
     * @return
     */
    private static List<String> getPerMissions(List<RoleRoutePermission> roleRoutePermissions, String origin){
        List<String> ret=new ArrayList<>();
        roleRoutePermissions.stream().forEach(p->{
            List<SysPermission> permissions=p.getPermissionsByRouteSign(origin);
            List<String> permissionSigns= permissions.stream().map(o -> o.getSign()).collect(Collectors.toList());
            ret.addAll(permissionSigns);
        });
        return ret;
    }
}
