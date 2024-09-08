package com.athena.security.servlet.client.wechat;

import com.athena.security.servlet.client.config.ClientSecurityConstants;
import com.athena.security.servlet.client.delegate.IAuthorizationRequestCustomizer;
import jakarta.annotation.Resource;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriBuilder;

import java.net.URI;

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
     * 接受输入参数
     *
     * @param builder 授权请求构建器
     */
    @Override
    public void accept(OAuth2AuthorizationRequest.Builder builder) {
        // 微信 OAuth2 授权请求参数处理
        builder.authorizationRequestUri(this::authorizationRequestUriConsumer);
    }

    /**
     * 微信 OAuth2 授权请求参数处理
     *
     * @param uriBuilder URI 构建器
     * @return 处理后的 URI
     */
    private URI authorizationRequestUriConsumer(UriBuilder uriBuilder) {
        String uri = uriBuilder.build().getQuery();
        // 替换 client_id 为 appid
        uri = uri.replace(OAuth2ParameterNames.CLIENT_ID, ClientSecurityConstants.WECHAT_APP_ID);
        // 微信 OAuth2 授权请求参数添加 #wechat_redirect
        return uriBuilder.replaceQuery(uri).fragment(ClientSecurityConstants.WECHAT_REDIRECT).build();
    }
}
