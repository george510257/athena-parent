package com.athena.common.bean.jackson2;

import com.athena.common.bean.security.Role;
import com.athena.common.bean.security.User;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * Bean模块
 *
 * @author george
 */
public class BeanModule extends SimpleModule {

    /**
     * 构造函数
     */
    public BeanModule() {
        super(BeanModule.class.getName(), new Version(1, 0, 0, null, "com.athena.common", "athena-common-bean"));
    }

    /**
     * 设置模块
     *
     * @param context 上下文
     */
    @Override
    public void setupModule(SetupContext context) {
        context.setMixInAnnotations(User.class, UserMixin.class);
        context.setMixInAnnotations(Role.class, RoleMixin.class);
    }
}
