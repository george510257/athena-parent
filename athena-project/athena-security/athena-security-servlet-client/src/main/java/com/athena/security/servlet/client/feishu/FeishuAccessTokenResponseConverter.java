package com.athena.security.servlet.client.feishu;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.athena.security.servlet.client.delegate.IAccessTokenResponseConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.endpoint.DefaultMapOAuth2AccessTokenResponseConverter;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class FeishuAccessTokenResponseConverter implements IAccessTokenResponseConverter {

    /**
     * 默认 Map 类型 OAuth2 访问令牌响应转换器
     */
    private final DefaultMapOAuth2AccessTokenResponseConverter delegate = new DefaultMapOAuth2AccessTokenResponseConverter();

    /**
     * 将 Map 类型的响应参数转换为 OAuth2AccessTokenResponse 对象
     *
     * @param parameters 响应参数
     * @return OAuth2AccessTokenResponse 对象
     */
    @Override
    public OAuth2AccessTokenResponse convert(Map<String, Object> parameters) {
        log.info("FeishuMapAccessTokenResponseConverter convert parameters -> {}", JSONUtil.toJsonStr(parameters));
        return delegate.convert(MapUtil.get(parameters, "data", new TypeReference<>() {
        }));
    }

    /**
     * 测试是否支持指定的注册标识
     *
     * @param registrationId 注册标识
     * @return 是否支持
     */
    @Override
    public boolean test(String registrationId) {
        return "feishu".equals(registrationId);
    }
}
