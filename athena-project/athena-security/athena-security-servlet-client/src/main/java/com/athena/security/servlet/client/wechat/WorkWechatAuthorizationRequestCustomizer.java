package com.athena.security.servlet.client.wechat;

import com.athena.security.servlet.client.config.ClientSecurityConstants;
import com.athena.security.servlet.client.delegate.IAuthorizationRequestCustomizer;
import jakarta.annotation.Resource;
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
public class WorkWechatAuthorizationRequestCustomizer implements IAuthorizationRequestCustomizer {
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
     * 定制化
     *
     * @param builder 授权请求构建器
     */
    @Override
    public void accept(OAuth2AuthorizationRequest.Builder builder) {
        // 设置参数
        builder.parameters(this::convertParameters);
    }

    /**
     * 转换参数
     *
     * @param parameters 参数
     */
    private void convertParameters(Map<String, Object> parameters) {
        Map<String, Object> map = new HashMap<>(6);
        map.put(ClientSecurityConstants.WECHAT_WORK_LOGIN_TYPE, wechatProperties.getWork().getLoginType());
        map.put(ClientSecurityConstants.WECHAT_APP_ID, parameters.get(OAuth2ParameterNames.CLIENT_ID));
        map.put(ClientSecurityConstants.WECHAT_WORK_AGENT_ID, wechatProperties.getWork().getAgentId());
        map.put(OAuth2ParameterNames.REDIRECT_URI, parameters.get(OAuth2ParameterNames.REDIRECT_URI));
        map.put(OAuth2ParameterNames.STATE, parameters.get(OAuth2ParameterNames.STATE));
        map.put(ClientSecurityConstants.WECHAT_LANG, wechatProperties.getWork().getLang());
        parameters.clear();
        parameters.putAll(map);
    }
}
