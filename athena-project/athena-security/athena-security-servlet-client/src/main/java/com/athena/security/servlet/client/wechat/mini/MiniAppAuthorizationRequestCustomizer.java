package com.athena.security.servlet.client.wechat.mini;

import com.athena.security.servlet.client.delegate.IAuthorizationRequestCustomizer;
import com.athena.security.servlet.client.wechat.WechatProperties;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 小程序授权请求自定义器
 *
 * @author george
 */
@Component
public class MiniAppAuthorizationRequestCustomizer implements IAuthorizationRequestCustomizer {
    /**
     * 微信配置属性
     */
    @Resource
    private WechatProperties wechatProperties;


    /**
     * 测试是否支持指定的注册标识
     *
     * @param registrationId 注册标识
     * @return 是否支持
     */
    @Override
    public boolean test(String registrationId) {
        return wechatProperties.getMiniApp().getRegistrationId().equals(registrationId);
    }

    /**
     * 小程序 OAuth2 授权请求参数处理
     *
     * @param builder 构建器
     * @param request 请求
     */
    @Override
    public void accept(OAuth2AuthorizationRequest.Builder builder, HttpServletRequest request) {
        // 小程序 OAuth2 授权请求参数处理
        builder.parameters(parameters -> {
            Map<String, Object> result = new HashMap<>(2);
            result.put("code", request.getParameter("code"));
            result.put("state", parameters.get("state"));
            parameters.clear();
            parameters.putAll(result);
        });

    }
}
