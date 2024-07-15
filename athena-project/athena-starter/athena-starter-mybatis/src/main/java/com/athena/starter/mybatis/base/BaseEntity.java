package com.athena.starter.mybatis.base;

import com.athena.common.bean.base.IDomain;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
public abstract class BaseEntity implements IDomain {

    public static final String COL_ID = "id";
    public static final String COL_TENANT_ID = "tenant_id";
    public static final String COL_VERSION = "version";
    public static final String COL_DELETED = "deleted";
    public static final String COL_CREATE_USER_ID = "create_user_id";
    public static final String COL_CREATE_USER_NAME = "create_user_name";
    public static final String COL_CREATE_TIME = "create_time";
    public static final String COL_UPDATE_USER_ID = "update_user_id";
    public static final String COL_UPDATE_USER_NAME = "update_user_name";
    public static final String COL_UPDATE_TIME = "update_time";

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 版本号
     */
    @Version
    private Integer version;

    /**
     * 删除标记 0:正常;1:已删除
     */
    private Boolean deleted;

    /**
     * 创建人ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    /**
     * 创建人姓名
     */
    @TableField(fill = FieldFill.INSERT)
    private String createUserName;

    /**
     * 创建时间
     */
    @TableField(insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    private Date createTime;

    /**
     * 修改人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUserId;

    /**
     * 修改人姓名
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUserName;

    /**
     * 更新时间
     */
    @TableField(insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    private Date updateTime;
}
