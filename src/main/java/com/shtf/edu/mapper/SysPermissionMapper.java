package com.shtf.edu.mapper;

import com.shtf.edu.bean.entity.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author luobo
 * @since 2021-04-29
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    List<SysPermission> selectListByRoleUuIdAndRouteUuid(@Param("roleUuid")String roleUuid,@Param("routeUuid")String routeUuid);
}
