package com.gls.athena.security.servlet.client.delegate;

import cn.hutool.core.map.MapUtil;
import com.gls.athena.security.servlet.client.config.ClientSecurityConstants;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 委托授权码令牌响应客户端
 *
 * @author george
 */
@Component
public class DelegateAuthorizationCodeTokenResponseClient implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {

    /**
     * 默认授权码令牌响应客户端
     */
    private static final DefaultAuthorizationCodeTokenResponseClient DEFAULT = new DefaultAuthorizationCodeTokenResponseClient();
    /**
     * OAuth2 客户端属性映射器提供者
     */
    @Resource
    private ObjectProvider<IAuthorizationCodeTokenResponseClientAdapter> adapters;

    /**
     * 获取访问令牌响应
     *
     * @param authorizationCodeGrantRequest 授权码授权请求
     * @return 访问令牌响应
     */
    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
        Map<String, Object> metadata = authorizationCodeGrantRequest.getClientRegistration().getProviderDetails().getConfigurationMetadata();
        // 获取提供者
        String providerId = MapUtil.getStr(metadata, ClientSecurityConstants.PROVIDER_ID);
        // 获取适配器
        return adapters.stream().filter(adapter -> adapter.test(providerId)).findFirst()
                .map(adapter -> adapter.getTokenResponse(authorizationCodeGrantRequest))
                .orElseGet(() -> DEFAULT.getTokenResponse(authorizationCodeGrantRequest));
    }

}
