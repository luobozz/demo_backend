package com.shtf.edu.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.sql.Date;
import java.io.Serializable;
import lombok.Data;

/**
 * SysPermission class
 *
 * 权限
 *
 * id,uuid,title,sign,content,del_flag,created_by,created_time,updated_by,updated_time,
 * @author luobo
 * @date 2021-04-29
 */
@Data
public class SysPermission implements Serializable {

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
     * 权限标题
     */
    private String title;

    /**
     * 权限标识
     */
    private String sign;

    /**
     * 权限描述内容
     */
    private String content;

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
