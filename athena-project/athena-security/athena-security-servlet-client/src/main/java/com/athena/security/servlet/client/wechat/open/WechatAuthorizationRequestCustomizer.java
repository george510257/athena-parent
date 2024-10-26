package com.athena.security.servlet.client.wechat.open;

import com.athena.security.servlet.client.delegate.IAuthorizationRequestCustomizer;
import com.athena.security.servlet.client.wechat.WechatProperties;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

/**
 * 微信 OAuth2 授权请求自定义器
 *
 * @author george
 */
@Component
public class WechatAuthorizationRequestCustomizer implements IAuthorizationRequestCustomizer {
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
        return wechatProperties.getMp().getRegistrationId().equals(registrationId)
                || wechatProperties.getOpen().getRegistrationId().equals(registrationId);
    }

    /**
     * 微信 OAuth2 授权请求参数处理
     *
     * @param builder 构建器
     * @param request 请求
     */
    @Override
    public void accept(OAuth2AuthorizationRequest.Builder builder, HttpServletRequest request) {
        // 微信 OAuth2 授权请求参数处理
        builder.authorizationRequestUri(uriBuilder -> {
            // 构建 URI
            String uri = uriBuilder.build().getQuery();
            // 替换 client_id 为 appid
            uri = uri.replace("client_id", "appId");
            // 微信 OAuth2 授权请求参数添加 #wechat_redirect
            return uriBuilder.replaceQuery(uri).fragment("wechat_redirect").build();
        });
    }
}
