package com.athena.security.servlet.client.feishu;

import com.athena.security.servlet.client.delegate.IOAuth2UserServiceCustomizer;
import jakarta.annotation.Resource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FeishuOAuth2UserServiceCustomizer implements IOAuth2UserServiceCustomizer {
    /**
     * 飞书属性配置
     */
    @Resource
    private FeishuProperties feishuProperties;

    @Override
    public boolean test(String registrationId) {
        return feishuProperties.getRegistrationId().equals(registrationId);
    }

    @Override
    public void customize(DefaultOAuth2UserService oauth2UserService) {
        oauth2UserService.setAttributesConverter(this::attributesConverter);
    }

    private Converter<Map<String, Object>, Map<String, Object>> attributesConverter(OAuth2UserRequest oAuth2UserRequest) {
        // 获取数据
        return parameters -> (Map<String, Object>) parameters.get("data");
    }
}
