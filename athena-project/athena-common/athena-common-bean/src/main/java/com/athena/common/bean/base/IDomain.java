package com.athena.common.bean.base;

import java.io.Serializable;
import java.util.Date;

/**
 * 领域对象
 */
public interface IDomain extends Serializable {
    /**
     * 获取ID
     *
     * @return ID
     */
    Long getId();

    /**
     * 设置ID
     *
     * @param id ID
     */
    void setId(Long id);

    /**
     * 获取租户ID
     *
     * @return 租户ID
     */
    Long getTenantId();

    /**
     * 设置租户ID
     *
     * @param tenantId 租户ID
     */
    void setTenantId(Long tenantId);

    /**
     * 获取版本号
     *
     * @return 版本号
     */
    Integer getVersion();

    /**
     * 设置版本号
     *
     * @param version 版本号
     */
    void setVersion(Integer version);

    /**
     * 获取删除标记 0:正常;1:已删除
     *
     * @return 删除标记
     */
    Boolean getDeleted();

    /**
     * 设置删除标记 0:正常;1:已删除
     *
     * @param deleted 删除标记
     */
    void setDeleted(Boolean deleted);

    /**
     * 获取创建人ID
     *
     * @return 创建人ID
     */
    Long getCreateUserId();

    /**
     * 设置创建人ID
     *
     * @param createUserId 创建人ID
     */
    void setCreateUserId(Long createUserId);

    /**
     * 获取创建人姓名
     *
     * @return 创建人姓名
     */
    String getCreateUserName();

    /**
     * 设置创建人姓名
     *
     * @param createUserName 创建人姓名
     */
    void setCreateUserName(String createUserName);

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    Date getCreateTime();

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    void setCreateTime(Date createTime);

    /**
     * 获取更新人ID
     *
     * @return 更新人ID
     */
    Long getUpdateUserId();

    /**
     * 设置更新人ID
     *
     * @param updateUserId 更新人ID
     */
    void setUpdateUserId(Long updateUserId);

    /**
     * 获取更新人姓名
     *
     * @return 更新人姓名
     */
    String getUpdateUserName();

    /**
     * 设置更新人姓名
     *
     * @param updateUserName 更新人姓名
     */
    void setUpdateUserName(String updateUserName);

    /**
     * 获取更新时间
     *
     * @return 更新时间
     */
    Date getUpdateTime();

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    void setUpdateTime(Date updateTime);
}
