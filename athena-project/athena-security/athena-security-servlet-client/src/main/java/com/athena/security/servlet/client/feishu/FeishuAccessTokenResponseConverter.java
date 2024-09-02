package com.athena.security.servlet.client.feishu;

import cn.hutool.json.JSONUtil;
import com.athena.security.servlet.client.delegate.IAccessTokenResponseConverter;
import com.athena.security.servlet.client.feishu.domian.FeishuProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.core.endpoint.DefaultMapOAuth2AccessTokenResponseConverter;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 飞书访问令牌响应转换器
 *
 * @author george
 */
@Slf4j
@Component
public class FeishuAccessTokenResponseConverter implements IAccessTokenResponseConverter {

    /**
     * 默认 Map 类型 OAuth2 访问令牌响应转换器
     */
    private final DefaultMapOAuth2AccessTokenResponseConverter delegate = new DefaultMapOAuth2AccessTokenResponseConverter();
    /**
     * 飞书属性配置
     */
    @Resource
    private FeishuProperties feishuProperties;

    /**
     * 将 Map 类型的响应参数转换为 OAuth2AccessTokenResponse 对象
     *
     * @param parameters 响应参数
     * @return OAuth2AccessTokenResponse 对象
     */
    @Override
    public OAuth2AccessTokenResponse convert(@NonNull Map<String, Object> parameters) {
        log.info("FeishuMapAccessTokenResponseConverter convert parameters -> {}", JSONUtil.toJsonStr(parameters));
        // 获取 data 节点数据
        Map<String, Object> data = (Map<String, Object>) parameters.get("data");
        // 获取访问令牌
        return delegate.convert(data);
    }

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
}
