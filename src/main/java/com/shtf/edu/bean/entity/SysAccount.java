package com.shtf.edu.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.sql.Date;
import java.io.Serializable;
import lombok.Data;

/**
 * SysAccount class
 *
 * 账户
 *
 * id,uuid,account,tel,password,salt,avatar,status,del_flag,created_by,created_time,updated_by,updated_time,untitled,
 * @author luobo
 * @date 2021-04-29
 */
@Data
public class SysAccount implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * UUID UUID
     */
    private String uuid;

    /**
     * 账号
     */
    private String account;

    /**
     * 手机号
     */
    private String tel;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 账号状态 表达冻结等账号状态
     */
    private String status;

    /**
     * 删除标记 删除标记
     */
    private Integer delFlag;

    /**
     * 创建人 创建人
     */
    private String createdBy;

    /**
     * 创建时间 创建时间
     */
    private Date createdTime;

    /**
     * 更新人 更新人
     */
    private String updatedBy;

    /**
     * 更新时间 更新时间
     */
    private Date updatedTime;

}
