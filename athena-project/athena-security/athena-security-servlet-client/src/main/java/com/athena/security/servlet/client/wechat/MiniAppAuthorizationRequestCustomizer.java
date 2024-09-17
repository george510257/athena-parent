package com.athena.security.servlet.client.wechat;

import com.athena.security.servlet.client.delegate.IAuthorizationRequestCustomizer;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 小程序授权请求自定义器
 *
 * @author george
 */
@Component
public class MiniAppAuthorizationRequestCustomizer implements IAuthorizationRequestCustomizer {
    @Resource
    private WechatProperties wechatProperties;
    @Resource
    private HttpServletRequest request;

    @Override
    public void accept(OAuth2AuthorizationRequest.Builder builder) {
        builder.parameters(this::parametersConsumer);
    }

    private void parametersConsumer(Map<String, Object> parameters) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", request.getParameter("code"));
        result.put("state", parameters.get("state"));
        parameters.clear();
        parameters.putAll(result);
    }

    @Override
    public boolean test(String registrationId) {
        return wechatProperties.getMiniApp().getRegistrationId().equals(registrationId);
    }
}
