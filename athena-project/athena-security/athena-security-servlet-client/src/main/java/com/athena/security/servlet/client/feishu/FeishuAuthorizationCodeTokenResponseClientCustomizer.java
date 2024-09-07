package com.athena.security.servlet.client.feishu;

import com.athena.security.servlet.client.delegate.IAuthorizationCodeTokenResponseClientCustomizer;
import jakarta.annotation.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.endpoint.DefaultMapOAuth2AccessTokenResponseConverter;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 飞书授权码令牌响应客户端定制器
 *
 * @author george
 */
@Component
public class FeishuAuthorizationCodeTokenResponseClientCustomizer implements IAuthorizationCodeTokenResponseClientCustomizer {
    /**
     * 飞书助手
     */
    @Resource
    private FeishuHelper feishuHelper;
    /**
     * 飞书属性配置
     */
    @Resource
    private FeishuProperties feishuProperties;

    /**
     * 测试是否支持指定的注册标识
     *
     * @param registrationId 注册标识
     * @return 是否支持
     */
    @Override
    public boolean test(String registrationId) {
        return feishuProperties.getRegistrationId().equals(registrationId);
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
     * 获取 RestOperations
     *
     * @return RestOperations
     */
    private RestOperations getRestOperations() {
        RestTemplate restTemplate = new RestTemplate();
        // 设置消息转换器
        restTemplate.setMessageConverters(this.getMessageConverters());
        return restTemplate;
    }

    /**
     * 获取消息转换器
     *
     * @return 消息转换器
     */
    private List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        // 添加消息转换器
        OAuth2AccessTokenResponseHttpMessageConverter converter = new OAuth2AccessTokenResponseHttpMessageConverter();
        converter.setAccessTokenResponseConverter(this::accessTokenResponseConverter);
        messageConverters.add(converter);
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        return messageConverters;
    }

    /**
     * 访问令牌响应转换器
     *
     * @param parameters 响应参数
     * @return 访问令牌响应
     */
    private OAuth2AccessTokenResponse accessTokenResponseConverter(Map<String, Object> parameters) {
        // 获取 data 节点数据
        Map<String, Object> data = (Map<String, Object>) parameters.get("data");
        // 获取访问令牌
        return new DefaultMapOAuth2AccessTokenResponseConverter().convert(data);

    }

    /**
     * 请求实体转换器
     *
     * @param request 授权码授权请求
     * @return 请求实体
     */
    private RequestEntity<?> requestEntityConverter(OAuth2AuthorizationCodeGrantRequest request) {
        // 请求头
        HttpHeaders headers = this.convertHeaders(request);
        // 请求参数
        Map<String, String> parameters = this.convertParameters(request);
        // 请求地址
        URI uri = URI.create(request.getClientRegistration().getProviderDetails().getTokenUri());
        // 创建请求实体
        return RequestEntity.post(uri)
                .headers(headers)
                .body(parameters);
    }

    /**
     * 转换请求头
     *
     * @param request 授权码授权请求
     * @return 请求头
     */
    private HttpHeaders convertHeaders(OAuth2AuthorizationCodeGrantRequest request) {
        // 客户端注册
        ClientRegistration clientRegistration = request.getClientRegistration();
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        // 设置请求头
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        // 设置授权头
        headers.setBearerAuth(feishuHelper.getAppAccessToken(clientRegistration.getClientId(), clientRegistration.getClientSecret()));
        // 返回请求头
        return headers;
    }

    /**
     * 转换参数
     *
     * @param request 授权码授权请求
     * @return 参数
     */
    private Map<String, String> convertParameters(OAuth2AuthorizationCodeGrantRequest request) {
        Map<String, String> parameters = new HashMap<>(2);
        OAuth2AuthorizationExchange authorizationExchange = request.getAuthorizationExchange();
        // 授权类型
        parameters.put(OAuth2ParameterNames.GRANT_TYPE, request.getGrantType().getValue());
        // 授权码
        parameters.put(OAuth2ParameterNames.CODE, authorizationExchange.getAuthorizationResponse().getCode());
        // 返回参数
        return parameters;
    }
}
