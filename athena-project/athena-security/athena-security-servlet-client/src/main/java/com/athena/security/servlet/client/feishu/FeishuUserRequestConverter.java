package com.athena.security.servlet.client.feishu;

import com.athena.security.servlet.client.delegate.IUserRequestConverter;
import com.athena.security.servlet.client.feishu.domian.FeishuProperties;
import jakarta.annotation.Resource;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequestEntityConverter;
import org.springframework.stereotype.Component;

/**
 * 飞书用户请求转换器
 */
@Component
public class FeishuUserRequestConverter implements IUserRequestConverter {
    /**
     * 飞书属性配置
     */
    private final OAuth2UserRequestEntityConverter converter = new OAuth2UserRequestEntityConverter();

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
        // 判断是否为飞书注册标识
        return feishuProperties.getRegistrationId().equals(registrationId);
    }

    /**
     * 转换为请求实体
     *
     * @param oauth2UserRequest 用户请求
     * @return 请求实体
     */
    @Override
    public RequestEntity<?> convert(OAuth2UserRequest oauth2UserRequest) {
        // 转换为请求实体
        return converter.convert(oauth2UserRequest);
    }
}
