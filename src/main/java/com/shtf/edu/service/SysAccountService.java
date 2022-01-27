package com.shtf.edu.service;

import com.shtf.edu.bean.api.system.account.vo.RoleRoutePermission;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SysAccountService class
 *
 * @author chenlingyu
 * @date 2021/5/4 20:38
 */
public interface SysAccountService {
    List<RoleRoutePermission> getRoleRotePermissionByRoleUuIds(List<String> uuIds);
}
