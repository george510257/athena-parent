package com.athena.security.servlet.client.feishu;

import com.athena.security.servlet.client.delegate.IAuthorizationRequestCustomizer;
import jakarta.annotation.Resource;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriBuilder;

import java.net.URI;

/**
 * 飞书 OAuth2 授权请求自定义器
 *
 * @author george
 */
@Component
public class FeishuAuthorizationRequestCustomizer implements IAuthorizationRequestCustomizer {
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
     * 自定义 OAuth2 授权请求
     *
     * @param builder 构建器
     */
    @Override
    public void accept(OAuth2AuthorizationRequest.Builder builder) {
        // 飞书 OAuth2 授权请求参数处理
        builder.authorizationRequestUri(this::authorizationRequestUriConsumer);
    }

    /**
     * 飞书 OAuth2 授权请求参数处理
     *
     * @param uriBuilder URI 构建器
     * @return 处理后的 URI
     */
    private URI authorizationRequestUriConsumer(UriBuilder uriBuilder) {
        String uri = uriBuilder.build().getQuery();
        // 替换 client_id 为 app_id
        uri = uri.replace(OAuth2ParameterNames.CLIENT_ID, "app_id");
        // 飞书 OAuth2 授权请求参数处理
        return uriBuilder.replaceQuery(uri).build();
    }
}
