package com.athena.security.servlet.client.wechat.work;

import com.athena.security.servlet.client.delegate.IAuthorizationRequestCustomizer;
import com.athena.security.servlet.client.wechat.WechatProperties;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 企业微信授权请求自定义器
 *
 * @author george
 */
@Component
public class WorkAuthorizationRequestCustomizer implements IAuthorizationRequestCustomizer {
    /**
     * 企业微信属性配置
     */
    @Resource
    private WechatProperties wechatProperties;

    /**
     * 测试是否支持指定的注册标识
     *
     * @param registrationId 注册标识
     * @return 是否支持
     */
    @Override
    public boolean test(String registrationId) {
        return wechatProperties.getWork().getRegistrationId().equals(registrationId);
    }

    /**
     * 企业微信 OAuth2 授权请求参数处理
     *
     * @param builder 构建器
     * @param request 请求
     */
    @Override
    public void accept(OAuth2AuthorizationRequest.Builder builder, HttpServletRequest request) {
        // 企业微信 OAuth2 授权请求参数处理
        builder.parameters(parameters -> {
            // 企业微信 OAuth2 授权请求参数处理
            Map<String, Object> map = new HashMap<>(6);
            map.put("login_type", wechatProperties.getWork().getLoginType());
            map.put("appId", parameters.get(OAuth2ParameterNames.CLIENT_ID));
            map.put("agentid", wechatProperties.getWork().getAgentId());
            map.put("redirect_uri", parameters.get(OAuth2ParameterNames.REDIRECT_URI));
            map.put("state", parameters.get(OAuth2ParameterNames.STATE));
            map.put("lang", wechatProperties.getWork().getLang());
            parameters.clear();
            parameters.putAll(map);
        });
    }
}
