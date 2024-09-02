package com.athena.security.servlet.client.weixin;

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
public class WeixinAuthorizationRequestCustomizer implements IAuthorizationRequestCustomizer {
    @Resource
    private WeixinProperties weixinProperties;

    @Override
    public boolean test(String registrationId) {
        return weixinProperties.getMpRegistrationId().equals(registrationId)
                || weixinProperties.getOpenRegistrationId().equals(registrationId);
    }

    @Override
    public void accept(OAuth2AuthorizationRequest.Builder builder) {
        // 微信 OAuth2 授权请求参数处理
        builder.authorizationRequestUri(this::authorizationRequestUriConsumer);
    }

    private URI authorizationRequestUriConsumer(UriBuilder uriBuilder) {
        String uri = uriBuilder.build().getQuery();
        // 替换 client_id 为 appid
        uri = uri.replace(OAuth2ParameterNames.CLIENT_ID, "appid");
        // 微信 OAuth2 授权请求参数添加 #wechat_redirect
        return uriBuilder.replaceQuery(uri).fragment("wechat_redirect").build();
    }
}
