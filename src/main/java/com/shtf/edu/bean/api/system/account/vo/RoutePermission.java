package com.shtf.edu.bean.api.system.account.vo;

import com.shtf.edu.bean.entity.SysPermission;
import lombok.Data;

import java.util.List;

/**
 * RotePermission class
 *
 * @author chenlingyu
 * @date 2021/5/4 20:47
 */
@Data
public class RoutePermission {
    private String uuid;
    private String name;
    private String sign;
    private List<SysPermission> permissions;
}
