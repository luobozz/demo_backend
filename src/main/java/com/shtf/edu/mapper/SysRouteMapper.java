package com.shtf.edu.mapper;

import com.shtf.edu.bean.entity.SysRoute;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 路由 Mapper 接口
 * </p>
 *
 * @author luobo
 * @since 2021-04-29
 */
public interface SysRouteMapper extends BaseMapper<SysRoute> {

    List<SysRoute> selectListByRoleUuId(@Param("roleUuid")String roleUuid);
}
