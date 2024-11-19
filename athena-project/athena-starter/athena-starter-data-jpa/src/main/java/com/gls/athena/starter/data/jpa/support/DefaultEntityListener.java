package com.gls.athena.starter.data.jpa.support;

import cn.hutool.extra.spring.SpringUtil;
import com.gls.athena.common.bean.security.IUserHelper;
import com.gls.athena.common.core.constant.BaseConstants;
import com.gls.athena.starter.data.jpa.base.BaseEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 默认实体监听器
 *
 * @author george
 */
@Slf4j
@Component
public class DefaultEntityListener {
    /**
     * 新增前操作
     *
     * @param entity 实体
     * @param <E>    实体类型
     */
    @PrePersist
    public <E extends BaseEntity> void prePersist(E entity) {
        IUserHelper userHelper = SpringUtil.getBean(IUserHelper.class);
        log.info("prePersist entity: {}", entity);
        Long userId = userHelper.getCurrentUserId().orElse(BaseConstants.DEFAULT_USER_ID);
        String userRealName = userHelper.getCurrentUserRealName().orElse(BaseConstants.DEFAULT_USER_USERNAME);
        Long tenantId = userHelper.getCurrentUserTenantId().orElse(BaseConstants.DEFAULT_TENANT_ID);
        Date now = new Date();
        entity.setTenantId(tenantId);
        entity.setDeleted(false);
        entity.setCreateUserId(userId);
        entity.setCreateUserName(userRealName);
        entity.setCreateTime(now);
        entity.setUpdateUserId(userId);
        entity.setUpdateUserName(userRealName);
        entity.setUpdateTime(now);
    }

    /**
     * 更新前操作
     *
     * @param entity 实体
     * @param <E>    实体类型
     */
    @PreUpdate
    public <E extends BaseEntity> void preUpdate(E entity) {
        IUserHelper userHelper = SpringUtil.getBean(IUserHelper.class);
        log.info("preUpdate entity: {}", entity);
        Long userId = userHelper.getCurrentUserId().orElse(BaseConstants.DEFAULT_USER_ID);
        String userRealName = userHelper.getCurrentUserRealName().orElse(BaseConstants.DEFAULT_USER_USERNAME);
        Date now = new Date();
        entity.setUpdateUserId(userId);
        entity.setUpdateUserName(userRealName);
        entity.setUpdateTime(now);
    }
}
