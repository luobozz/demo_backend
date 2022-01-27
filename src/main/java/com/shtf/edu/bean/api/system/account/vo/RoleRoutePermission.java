package com.shtf.edu.bean.api.system.account.vo;

import com.shtf.edu.bean.entity.SysPermission;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * RoleRotePermission class
 *
 * @author chenlingyu
 * @date 2021/5/4 20:46
 */
@Data
public class RoleRoutePermission {
    private String uuid;
    private String name;
    private String sign;
    private List<RoutePermission> routePermissions =new ArrayList<>();

    public List<SysPermission> getPermissionsByRouteSign(String routeSign){
        List<SysPermission> permissions=new ArrayList<>();
        for(int i = 0; i<this.routePermissions.size(); i++){
            RoutePermission routePermission =this.routePermissions.get(i);
            if(routePermission.getSign().equals(routeSign)){
                permissions= routePermission.getPermissions();
                break;
            }
        }
        return permissions;
    }
}
