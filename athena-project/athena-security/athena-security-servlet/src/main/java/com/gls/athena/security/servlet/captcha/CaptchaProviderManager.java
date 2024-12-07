package com.gls.athena.security.servlet.captcha;

import lombok.RequiredArgsConstructor;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;

/**
 * 验证码管理器
 *
 * @author george
 */
@RequiredArgsConstructor
public class CaptchaProviderManager {

    /**
     * 验证码提供器列表
     */
    private final List<CaptchaProvider<?>> providers;

    /**
     * 获取验证码提供器
     *
     * @param request 请求
     * @return 验证码提供器
     */
    public CaptchaProvider<?> getProvider(ServletWebRequest request) {
        return providers.stream()
                .filter(provider -> provider.support(request))
                .findFirst()
                .orElse(null);
    }

}
