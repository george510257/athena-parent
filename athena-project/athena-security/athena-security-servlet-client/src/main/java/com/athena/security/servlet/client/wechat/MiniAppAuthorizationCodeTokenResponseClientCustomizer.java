package com.athena.security.servlet.client.wechat;

import com.athena.security.servlet.client.config.ClientSecurityConstants;
import com.athena.security.servlet.client.delegate.IAuthorizationCodeTokenResponseClientCustomizer;
import jakarta.annotation.Resource;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * 小程序授权码令牌响应客户端定制器
 *
 * @author george
 */
@Component
public class MiniAppAuthorizationCodeTokenResponseClientCustomizer implements IAuthorizationCodeTokenResponseClientCustomizer {
    /**
     * 微信配置属性
     */
    @Resource
    private WechatProperties wechatProperties;

    @Override
    public boolean test(String registrationId) {
        return wechatProperties.getMiniApp().getRegistrationId().equals(registrationId);
    }

    @Override
    public void customize(DefaultAuthorizationCodeTokenResponseClient client) {
        // 设置请求实体转换器
        client.setRequestEntityConverter(this::requestEntityConverter);

    }

    private RequestEntity<?> requestEntityConverter(OAuth2AuthorizationCodeGrantRequest request) {
        // 请求参数
        MultiValueMap<String, String> parameters = this.convertParameters(request);
        // 请求地址
        URI uri = UriComponentsBuilder.fromUriString(request.getClientRegistration().getProviderDetails().getTokenUri())
                .queryParams(parameters).build().toUri();
        // 创建请求实体
        return RequestEntity.get(uri).build();
    }

    private MultiValueMap<String, String> convertParameters(OAuth2AuthorizationCodeGrantRequest request) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        // 客户端 ID
        parameters.add(ClientSecurityConstants.WECHAT_APP_ID, request.getClientRegistration().getClientId());
        // 客户端密钥
        parameters.add(ClientSecurityConstants.WECHAT_APP_SECRET, request.getClientRegistration().getClientSecret());
        // 授权类型
        parameters.add(OAuth2ParameterNames.GRANT_TYPE, request.getGrantType().getValue());
        // 授权码
        parameters.add(ClientSecurityConstants.MINI_APP_CODE, request.getAuthorizationExchange().getAuthorizationResponse().getCode());
        return parameters;
    }
}
