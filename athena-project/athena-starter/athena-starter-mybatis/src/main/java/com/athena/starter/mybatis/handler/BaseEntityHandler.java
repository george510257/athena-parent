package com.athena.starter.mybatis.handler;

import com.athena.common.bean.security.IUserHelper;
import com.athena.common.core.constant.BaseConstants;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 基础实体处理器
 */
@Slf4j
@Component
public class BaseEntityHandler implements MetaObjectHandler {

    /**
     * 用户帮助类
     */
    @Resource
    private IUserHelper userHelper;

    /**
     * 插入填充
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("insertFill metaObject: {}", metaObject);
        Long userId = userHelper.getCurrentUserId().orElse(BaseConstants.DEFAULT_USER_ID);
        String userName = userHelper.getCurrentUserNickName().orElse(BaseConstants.DEFAULT_USER_USERNAME);
        Date now = new Date();
        this.strictInsertFill(metaObject, "deleted", Boolean.class, false);
        this.strictInsertFill(metaObject, "createUserId", Long.class, userId);
        this.strictInsertFill(metaObject, "createUserName", String.class, userName);
        this.strictInsertFill(metaObject, "createTime", Date.class, now);
        this.strictInsertFill(metaObject, "updateUserId", Long.class, userId);
        this.strictInsertFill(metaObject, "updateUserName", String.class, userName);
        this.strictInsertFill(metaObject, "updateTime", Date.class, now);
    }

    /**
     * 更新填充
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("updateFill metaObject: {}", metaObject);
        Long userId = userHelper.getCurrentUserId().orElse(BaseConstants.DEFAULT_USER_ID);
        String userName = userHelper.getCurrentUserNickName().orElse(BaseConstants.DEFAULT_USER_USERNAME);
        Date now = new Date();
        this.strictInsertFill(metaObject, "updateUserId", Long.class, userId);
        this.strictInsertFill(metaObject, "updateUserName", String.class, userName);
        this.strictUpdateFill(metaObject, "updateTime", Date.class, now);
    }
}
