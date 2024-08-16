package com.athena.security.servlet.customizer;

import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 异常处理自定义器
 */
@Component
public class ExceptionHandlingCustomizer implements Customizer<ExceptionHandlingConfigurer<HttpSecurity>> {
    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(ExceptionHandlingConfigurer<HttpSecurity> configurer) {
        // 配置异常处理 - 登录入口点
        configurer.defaultAuthenticationEntryPointFor(new LoginUrlAuthenticationEntryPoint("/login"), createRequestMatcher());
    }

    /**
     * 创建请求匹配器 - 仅匹配HTML请求
     *
     * @return 请求匹配器
     */
    private RequestMatcher createRequestMatcher() {
        MediaTypeRequestMatcher requestMatcher = new MediaTypeRequestMatcher(MediaType.TEXT_HTML);
        requestMatcher.setIgnoredMediaTypes(Set.of(MediaType.ALL));
        return requestMatcher;
    }
}
