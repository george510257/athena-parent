package com.athena.security.servlet.client.feishu;

import com.athena.security.servlet.client.base.IAuthorizationRequestCustomizer;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 飞书 OAuth2 授权请求自定义器
 */
@Component
public class FeishuAuthorizationRequestCustomizer implements IAuthorizationRequestCustomizer {
    @Override
    public boolean test(String registrationId) {
        return "feishu".equals(registrationId);
    }

    @Override
    public void accept(OAuth2AuthorizationRequest.Builder builder) {
        builder.parameters(this::parametersConsumer);
    }

    private void parametersConsumer(Map<String, Object> parameters) {
        parameters.put("app_id", parameters.get("client_id"));
    }

}
