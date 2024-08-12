package com.athena.common.bean.jackson2;

import com.athena.common.bean.security.Role;
import com.athena.common.bean.security.User;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class BeanModule extends SimpleModule {

    public BeanModule() {
        super(BeanModule.class.getName(), new Version(1, 0, 0, null, "com.athena.common", "athena-common-bean"));
    }

    @Override
    public void setupModule(SetupContext context) {
        context.setMixInAnnotations(User.class, UserMixin.class);
        context.setMixInAnnotations(Role.class, RoleMixin.class);
    }
}
