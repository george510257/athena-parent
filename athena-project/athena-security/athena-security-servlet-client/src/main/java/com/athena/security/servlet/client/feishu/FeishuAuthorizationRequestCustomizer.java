package com.athena.security.servlet.client.feishu;

import com.athena.security.servlet.client.delegate.IAuthorizationRequestCustomizer;
import com.athena.security.servlet.client.feishu.domian.FeishuProperties;
import jakarta.annotation.Resource;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 飞书 OAuth2 授权请求自定义器
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
        builder.parameters(this::parametersConsumer);
    }

    /**
     * 参数消费者
     *
     * @param parameters 参数
     */
    private void parametersConsumer(Map<String, Object> parameters) {
        // 飞书 OAuth2 授权请求参数 app_id 与 client_id 一致
        parameters.put("app_id", parameters.get("client_id"));
        parameters.remove("client_id");
    }

}
