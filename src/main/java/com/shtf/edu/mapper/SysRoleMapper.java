package com.shtf.edu.mapper;

import com.shtf.edu.bean.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author luobo
 * @since 2021-04-29
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> selectListByAccountUuId(@Param("accountUuid") String accountUuid);
}
