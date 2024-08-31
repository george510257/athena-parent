package com.athena.security.servlet.client.feishu;

import com.athena.security.servlet.client.delegate.IUserResponseConverter;
import com.athena.security.servlet.client.feishu.domian.FeishuProperties;
import jakarta.annotation.Resource;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 飞书用户响应转换器
 */
@Component
public class FeishuUserResponseConverter implements IUserResponseConverter {

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
     * 转换
     *
     * @param oauth2UserRequest 用户请求
     * @param params            参数
     * @return 转换结果
     */
    @Override
    public Map<String, Object> convert(OAuth2UserRequest oauth2UserRequest, Map<String, Object> params) {
        // 获取数据
        return (Map<String, Object>) params.get("data");
    }
}
