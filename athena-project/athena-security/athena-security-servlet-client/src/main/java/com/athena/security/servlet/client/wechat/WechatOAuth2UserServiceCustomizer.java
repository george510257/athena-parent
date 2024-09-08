package com.athena.security.servlet.client.wechat;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.athena.security.servlet.client.delegate.IOAuth2UserServiceCustomizer;
import jakarta.annotation.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * 微信 OAuth2UserService 定制器
 *
 * @author george
 */
@Component
public class WechatOAuth2UserServiceCustomizer implements IOAuth2UserServiceCustomizer {
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
     * 定制化
     *
     * @param oauth2UserService OAuth2 用户信息服务
     */
    @Override
    public void customize(DefaultOAuth2UserService oauth2UserService) {
        // 设置属性转换器
        oauth2UserService.setRequestEntityConverter(this::requestEntityConverter);
    }

    /**
     * 请求实体转换器
     *
     * @param request 授权码授权请求
     * @return 请求实体
     */
    private RequestEntity<?> requestEntityConverter(OAuth2UserRequest request) {
        // 请求头
        HttpHeaders headers = this.convertHeaders(request);
        // 请求参数
        MultiValueMap<String, String> parameters = this.convertParameters(request);
        // 请求 URI
        URI uri = UriComponentsBuilder.fromUriString(request.getClientRegistration()
                        .getProviderDetails()
                        .getUserInfoEndpoint()
                        .getUri())
                .queryParams(parameters)
                .build().toUri();
        return RequestEntity.get(uri)
                .headers(headers)
                .build();
    }

    /**
     * 转换请求头
     *
     * @param request 请求
     * @return 请求头
     */
    private HttpHeaders convertHeaders(OAuth2UserRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(CollUtil.toList(MediaType.APPLICATION_JSON));
        return headers;
    }

    /**
     * 转换请求参数
     *
     * @param request 请求
     * @return 请求参数
     */
    private MultiValueMap<String, String> convertParameters(OAuth2UserRequest request) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add(OAuth2ParameterNames.ACCESS_TOKEN, request.getAccessToken().getTokenValue());
        parameters.add("openid", StrUtil.toString(request.getAdditionalParameters().get("openid")));
        parameters.add("lang", "zh_CN");
        return parameters;
    }
}
