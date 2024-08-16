package com.athena.security.servlet.code;

import com.athena.security.servlet.code.base.VerificationCodeProvider;
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
    private List<VerificationCodeProvider<?>> providers;

    /**
     * 获取验证码提供器
     *
     * @param request 请求
     * @return 验证码提供器
     */
    public VerificationCodeProvider<?> getProvider(ServletWebRequest request) {
        return providers.stream()
                .filter(provider -> provider.support(request))
                .findFirst()
                .orElse(null);
    }

}
