package com.athena.security.servlet.client.wechat;

import cn.hutool.core.util.StrUtil;
import com.athena.security.servlet.client.delegate.IOAuth2UserServiceCustomizer;
import jakarta.annotation.Resource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

/**
 * 企业微信 OAuth2UserService 定制器
 *
 * @author george
 */
@Component
public class WorkWechatOAuth2UserServiceCustomizer implements IOAuth2UserServiceCustomizer {
    /**
     * 企业微信属性配置
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
        return wechatProperties.getWork().getRegistrationId().equals(registrationId);
    }

    /**
     * 定制化
     *
     * @param service OAuth2 用户信息服务
     */
    @Override
    public void customize(DefaultOAuth2UserService service) {
        // 设置请求实体转换器
        service.setRequestEntityConverter(this::requestEntityConverter);
        // 设置属性转换器
        service.setAttributesConverter(this::attributesConverter);
    }

    /**
     * 属性转换器
     *
     * @param request OAuth2 用户请求
     * @return 属性转换器
     */
    private Converter<Map<String, Object>, Map<String, Object>> attributesConverter(OAuth2UserRequest request) {
        return parameters -> {
            String userid = StrUtil.toString(parameters.get("userid"));
            if (StrUtil.isNotBlank(userid)) {
                parameters.put("id", "userid_" + userid);
            }
            String openId = StrUtil.toString(parameters.get("openid"));
            if (StrUtil.isNotBlank(openId)) {
                parameters.put("id", "openId_" + openId);
            }
            return parameters;
        };
    }

    /**
     * 请求实体转换器
     *
     * @param request OAuth2 用户请求
     * @return 请求实体
     */
    private RequestEntity<?> requestEntityConverter(OAuth2UserRequest request) {
        // 请求参数
        MultiValueMap<String, String> parameters = this.convertParameters(request);
        // 请求 URI
        URI uri = UriComponentsBuilder.fromUriString(request.getClientRegistration().getProviderDetails()
                        .getUserInfoEndpoint().getUri())
                .queryParams(parameters).build().toUri();
        return RequestEntity.get(uri).build();
    }

    /**
     * 转换请求参数
     *
     * @param request OAuth2 用户请求
     * @return 请求参数
     */
    private MultiValueMap<String, String> convertParameters(OAuth2UserRequest request) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add(OAuth2ParameterNames.ACCESS_TOKEN, request.getAccessToken().getTokenValue());
        parameters.add(OAuth2ParameterNames.CODE, StrUtil.toString(request.getAdditionalParameters().get(OAuth2ParameterNames.CODE)));
        return parameters;
    }
}
