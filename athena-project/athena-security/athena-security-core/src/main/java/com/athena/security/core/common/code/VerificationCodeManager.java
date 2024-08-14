package com.athena.security.core.common.code;

import com.athena.security.core.common.code.base.VerificationCodeProvider;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;

/**
 * 验证码管理器
 */
@Component
public class VerificationCodeManager {

    /**
     * 验证码提供器列表
     */
    @Resource
    private List<VerificationCodeProvider<?, ?, ?>> providers;

    /**
     * 获取验证码提供器
     *
     * @param request 请求
     * @return 验证码提供器
     */
    public VerificationCodeProvider<?, ?, ?> getProvider(ServletWebRequest request) {
        for (VerificationCodeProvider<?, ?, ?> provider : providers) {
            if (provider.support(request)) {
                return provider;
            }
        }
        return null;
    }

}
