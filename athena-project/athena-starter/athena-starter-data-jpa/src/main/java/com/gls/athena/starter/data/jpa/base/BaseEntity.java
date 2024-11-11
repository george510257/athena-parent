package com.gls.athena.starter.data.jpa.base;

import com.gls.athena.common.bean.base.IDomain;
import com.gls.athena.starter.data.jpa.support.DefaultEntityListener;
import com.gls.athena.starter.data.jpa.support.SnowflakeId;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Data;
import org.hibernate.annotations.Comment;

import java.util.Date;

/**
 * 基础实体类
 *
 * @author george
 */
@Data
@MappedSuperclass
@EntityListeners({DefaultEntityListener.class})
public abstract class BaseEntity implements IDomain {
    /**
     * 主键
     */
    @Id
    @SnowflakeId
    @Comment("主键")
    private Long id;
    /**
     * 租户ID
     */
    @Comment("租户ID")
    private Long tenantId;
    /**
     * 版本号
     */
    @Comment("版本号")
    @Version
    private Integer version;
    /**
     * 删除标记 0:正常;1:已删除
     */
    @Comment("删除标记 0:正常;1:已删除")
    private Boolean deleted;
    /**
     * 创建人ID
     */
    @Comment("创建人ID")
    private Long createUserId;
    /**
     * 创建人姓名
     */
    @Comment("创建人姓名")
    private String createUserName;
    /**
     * 创建时间
     */
    @Comment("创建时间")
    private Date createTime;
    /**
     * 修改人ID
     */
    @Comment("修改人ID")
    private Long updateUserId;
    /**
     * 修改人姓名
     */
    @Comment("修改人姓名")
    private String updateUserName;
    /**
     * 更新时间
     */
    @Comment("更新时间")
    private Date updateTime;
}
