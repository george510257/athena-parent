package com.athena.security.servlet.client.feishu;

import com.athena.security.servlet.client.delegate.IOAuth2UserServiceCustomizer;
import jakarta.annotation.Resource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 飞书 OAuth2 用户信息服务定制器
 *
 * @author George
 */
@Component
public class FeishuOAuth2UserServiceCustomizer implements IOAuth2UserServiceCustomizer {
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
     * @param oauth2UserService OAuth2 用户信息服务
     */
    @Override
    public void customize(DefaultOAuth2UserService oauth2UserService) {
        // 设置属性转换器
        oauth2UserService.setAttributesConverter(this::attributesConverter);
    }

    /**
     * 属性转换器
     *
     * @param oAuth2UserRequest OAuth2 用户请求
     * @return 转换器
     */
    private Converter<Map<String, Object>, Map<String, Object>> attributesConverter(OAuth2UserRequest oAuth2UserRequest) {
        // 获取数据
        return parameters -> (Map<String, Object>) parameters.get("data");
    }
}
