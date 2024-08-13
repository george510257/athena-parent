package com.athena.security.core.common.jackson2;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.security.web.csrf.DefaultCsrfToken;

/**
 * 安全核心模块
 */
public class SecurityCoreModule extends SimpleModule {
    /**
     * 构造方法
     */
    public SecurityCoreModule() {
        super(SecurityCoreModule.class.getName(), new Version(1, 0, 0, null, "com.athena.security", "athena-security-core"));
    }

    /**
     * 设置模块
     *
     * @param context 上下文
     */
    @Override
    public void setupModule(SetupContext context) {
        // CSRF令牌
        context.setMixInAnnotations(DefaultCsrfToken.class, CsrfTokenMixin.class);
    }
}
