package com.athena.security.servlet.client.wechat;

import cn.hutool.core.collection.CollUtil;
import com.athena.security.servlet.client.config.ClientSecurityConstants;
import com.athena.security.servlet.client.delegate.IAuthorizationCodeTokenResponseClientCustomizer;
import com.athena.security.servlet.client.wechat.domain.MiniAppAccessTokenResponse;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    /**
     * 小程序助手
     */
    @Resource
    private MiniAppHelper miniAppHelper;

    /**
     * 测试是否支持
     *
     * @param registrationId 客户端注册 ID
     * @return 是否支持
     */
    @Override
    public boolean test(String registrationId) {
        return wechatProperties.getMiniApp().getRegistrationId().equals(registrationId);
    }

    /**
     * 定制化
     *
     * @param client 授权码令牌响应客户端
     */
    @Override
    public void customize(DefaultAuthorizationCodeTokenResponseClient client) {
        // 设置请求实体转换器
        client.setRequestEntityConverter(this::requestEntityConverter);
        // 设置访问令牌响应转换器
        client.setRestOperations(this.getRestOperations());
    }

    /**
     * 请求实体转换器
     *
     * @return 请求实体
     */
    private RestOperations getRestOperations() {
        RestTemplate restTemplate = new RestTemplate();
        // 设置消息转换器
        restTemplate.setMessageConverters(this.getMessageConverters());
        return restTemplate;
    }

    /**
     * 请求实体转换器
     *
     * @return 请求实体
     */
    private List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        // 添加消息转换器
        OAuth2AccessTokenResponseHttpMessageConverter converter = new OAuth2AccessTokenResponseHttpMessageConverter();
        converter.setSupportedMediaTypes(CollUtil.newArrayList(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN));
        converter.setAccessTokenResponseConverter(this::accessTokenResponseConverter);
        messageConverters.add(converter);
        return messageConverters;
    }

    /**
     * 访问令牌响应转换器
     *
     * @param parameters 参数
     * @return 访问令牌响应
     */
    private OAuth2AccessTokenResponse accessTokenResponseConverter(Map<String, Object> parameters) {
        return OAuth2AccessTokenResponse.withToken("accessToken")
                .expiresIn(7200)
                .tokenType(OAuth2AccessToken.TokenType.BEARER)
                .additionalParameters(parameters)
                .build();
    }

    /**
     * 定制化响应
     *
     * @param request  授权码授权请求
     * @param response 响应
     * @return 定制化响应
     */
    @Override
    public OAuth2AccessTokenResponse customResponse(OAuth2AuthorizationCodeGrantRequest request, OAuth2AccessTokenResponse response) {
        // 获取小程序访问令牌
        MiniAppAccessTokenResponse miniAppAccessTokenResponse = miniAppHelper.getAppAccessToken(request.getClientRegistration().getClientId(), request.getClientRegistration().getClientSecret());
        // 构建响应
        return OAuth2AccessTokenResponse.withToken(miniAppAccessTokenResponse.getAccessToken())
                .expiresIn(miniAppAccessTokenResponse.getExpiresIn())
                .tokenType(OAuth2AccessToken.TokenType.BEARER)
                .additionalParameters(response.getAdditionalParameters())
                .build();
    }

    /**
     * 请求实体转换器
     *
     * @param request 授权码授权请求
     * @return 请求实体
     */
    private RequestEntity<?> requestEntityConverter(OAuth2AuthorizationCodeGrantRequest request) {
        // 请求参数
        MultiValueMap<String, String> parameters = this.convertParameters(request);
        // 请求地址
        URI uri = UriComponentsBuilder.fromUriString(request.getClientRegistration().getProviderDetails().getTokenUri())
                .queryParams(parameters).build().toUri();
        // 创建请求实体
        return RequestEntity.get(uri).build();
    }

    /**
     * 转换参数
     *
     * @param request 授权码授权请求
     * @return 参数
     */
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
