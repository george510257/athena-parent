package com.athena.upms.boot.web.entity;

import com.athena.starter.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * demo 实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_demo")
public class DemoEntity extends BaseEntity {

    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_STATUS = "status";
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 状态 0:正常 1:禁用
     */
    private Boolean status;
}
