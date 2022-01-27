package com.shtf.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shtf.edu.bean.api.system.account.vo.RoleRoutePermission;
import com.shtf.edu.bean.api.system.account.vo.RoutePermission;
import com.shtf.edu.bean.entity.SysRole;
import com.shtf.edu.bean.entity.SysRoute;
import com.shtf.edu.mapper.SysPermissionMapper;
import com.shtf.edu.mapper.SysRoleMapper;
import com.shtf.edu.mapper.SysRouteMapper;
import com.shtf.edu.service.SysAccountService;
import com.shtf.edu.utils.basis.BeanHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;

import java.util.List;

/**
 * SysAccountServiceImpl class
 *
 * @author chenlingyu
 * @date 2021/5/4 20:38
 */
@Service
public class SysAccountServiceImpl implements SysAccountService {

    @Autowired
    private SysRoleMapper roleMapper;
    @Autowired
    private SysRouteMapper sysRouteMapper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public List<RoleRoutePermission> getRoleRotePermissionByRoleUuIds(List<String> uuIds) {
        List<SysRole> roles = roleMapper.selectList(new QueryWrapper<SysRole>().in("uuid", uuIds));
        List<RoleRoutePermission> roleRoutePermissions = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roles)) {
            BeanHelper.copyProperties(roles, roleRoutePermissions);
            roleRoutePermissions.stream().forEach(p -> {
                List<SysRoute> roleRoutes = sysRouteMapper.selectListByRoleUuId(p.getUuid());
                List<RoutePermission> routePermissions = new ArrayList<>();
                roleRoutes.forEach(o -> {
                    RoutePermission routePermission = new RoutePermission();
                    BeanHelper.copyProperties(o, routePermission);
                    routePermission.setPermissions(sysPermissionMapper.selectListByRoleUuIdAndRouteUuid(p.getUuid(), o.getUuid()));
                    routePermissions.add(routePermission);
                });
                p.setRoutePermissions(routePermissions);
            });
        }
        return roleRoutePermissions;
    }
}
