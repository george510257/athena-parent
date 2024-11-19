package com.gls.athena.starter.mybatis.handler;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.gls.athena.common.bean.security.IUserHelper;
import com.gls.athena.common.core.constant.BaseConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 基础实体处理器
 *
 * @author george
 */
@Slf4j
@Component
public class BaseEntityHandler implements MetaObjectHandler {

    /**
     * 插入填充
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        IUserHelper userHelper = SpringUtil.getBean(IUserHelper.class);
        // 打印日志
        log.info("insertFill metaObject: {}", metaObject);
        // 获取当前用户 ID
        Long userId = userHelper.getCurrentUserId().orElse(BaseConstants.DEFAULT_USER_ID);
        // 获取当前用户昵称
        String userName = userHelper.getCurrentUserNickName().orElse(BaseConstants.DEFAULT_USER_USERNAME);
        // 获取当前时间
        Date now = new Date();
        // 严格插入填充
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
        IUserHelper userHelper = SpringUtil.getBean(IUserHelper.class);
        // 打印日志
        log.info("updateFill metaObject: {}", metaObject);
        // 获取当前用户 ID
        Long userId = userHelper.getCurrentUserId().orElse(BaseConstants.DEFAULT_USER_ID);
        // 获取当前用户昵称
        String userName = userHelper.getCurrentUserNickName().orElse(BaseConstants.DEFAULT_USER_USERNAME);
        // 获取当前时间
        Date now = new Date();
        // 严格更新填充
        this.strictInsertFill(metaObject, "updateUserId", Long.class, userId);
        this.strictInsertFill(metaObject, "updateUserName", String.class, userName);
        this.strictUpdateFill(metaObject, "updateTime", Date.class, now);
    }
}
