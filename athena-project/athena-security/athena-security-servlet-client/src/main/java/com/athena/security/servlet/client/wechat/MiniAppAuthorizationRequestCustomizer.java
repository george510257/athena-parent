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
    /**
     * 微信配置属性
     */
    @Resource
    private WechatProperties wechatProperties;
    /**
     * 请求
     */
    @Resource
    private HttpServletRequest request;

    /**
     * 接受输入参数
     *
     * @param builder 授权请求构建器
     */
    @Override
    public void accept(OAuth2AuthorizationRequest.Builder builder) {
        builder.parameters(this::parametersConsumer);
    }

    /**
     * 授权请求参数处理
     *
     * @param parameters 参数
     */
    private void parametersConsumer(Map<String, Object> parameters) {
        // 小程序授权请求参数 - code 和 state
        Map<String, Object> result = new HashMap<>(2);
        // code 为前端传递过来的 code
        result.put("code", request.getParameter("code"));
        // 后端生成 state
        result.put("state", parameters.get("state"));
        parameters.clear();
        parameters.putAll(result);
    }

    /**
     * 测试是否支持指定的注册标识
     *
     * @param registrationId 注册标识
     * @return 是否支持
     */
    @Override
    public boolean test(String registrationId) {
        return wechatProperties.getMiniApp().getRegistrationId().equals(registrationId);
    }
}
