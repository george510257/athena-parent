package com.gls.athena.security.servlet.client.support;

import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrations;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * 默认OAuth2客户端属性映射器
 *
 * @author george
 */
@Component
public class DefaultOAuth2ClientPropertiesMapper {
    /**
     * OAuth2客户端属性
     */
    @Resource
    private OAuth2ClientProperties properties;

    /**
     * 获取客户端注册信息Map
     *
     * @return 客户端注册信息Map
     */
    public Map<String, ClientRegistration> getClientRegistrations() {
        return this.properties.getRegistration().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry ->
                        getClientRegistration(entry.getKey(), entry.getValue())));
    }

    /**
     * 获取提供者
     *
     * @param registrationId 注册ID
     * @return 提供者
     */
    public String getProvider(String registrationId) {
        if (registrationId == null) {
            return null;
        }
        String provider = this.properties.getRegistration().get(registrationId).getProvider();
        if (provider == null) {
            return registrationId;
        }
        return provider;
    }

    /**
     * 转换为客户端注册信息
     *
     * @param registrationId 注册ID
     * @param registration   注册信息
     * @return 客户端注册信息
     */
    private ClientRegistration getClientRegistration(String registrationId, OAuth2ClientProperties.Registration registration) {
        // 获取提供者ID
        String providerId = getProvider(registrationId);
        // 获取构建器
        ClientRegistration.Builder builder = getBuilderByProvider(registrationId, providerId);
        // 映射属性
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(registrationId).to(builder::registrationId);
        map.from(registration::getClientId).to(builder::clientId);
        map.from(registration::getClientSecret).to(builder::clientSecret);
        map.from(registration::getClientAuthenticationMethod)
                .as(ClientAuthenticationMethod::new)
                .to(builder::clientAuthenticationMethod);
        map.from(registration::getAuthorizationGrantType)
                .as(AuthorizationGrantType::new)
                .to(builder::authorizationGrantType);
        map.from(registration::getRedirectUri).to(builder::redirectUri);
        map.from(registration::getScope).as(StringUtils::toStringArray).to(builder::scope);
        map.from(registration::getClientName).to(builder::clientName);
        return builder.build();
    }

    /**
     * 获取构建器
     *
     * @param registrationId 注册ID
     * @param providerId     提供者ID
     * @return 构建器
     */
    private ClientRegistration.Builder getBuilderByProvider(String registrationId, String providerId) {
        // 获取构建器 - 从提供者属性
        ClientRegistration.Builder builder = getBuilderByProperties(registrationId, providerId);
        if (builder == null) {
            // 获取构建器 - 从通用提供者
            builder = getBuilderByCommon(registrationId, providerId);
        }
        if (builder == null) {
            // 获取构建器 - 从默认提供者
            builder = getBuilderByDefault(registrationId, providerId);
        }
        if (builder == null) {
            // 未知提供者
            throw new IllegalArgumentException("Unknown provider: " + providerId);
        }
        // 映射属性
        return builder;
    }

    /**
     * 从默认提供者获取构建器
     *
     * @param registrationId 注册ID
     * @param providerId     提供者ID
     * @return 构建器
     */
    private ClientRegistration.Builder getBuilderByDefault(String registrationId, String providerId) {
        try {
            DefaultOAuth2Provider defaultProvider = DefaultOAuth2Provider.valueOf(providerId.toUpperCase());
            return defaultProvider.getBuilder(registrationId);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 从通用提供者获取构建器
     *
     * @param registrationId 注册ID
     * @param providerId     提供者ID
     * @return 构建器
     */
    private ClientRegistration.Builder getBuilderByCommon(String registrationId, String providerId) {
        try {
            CommonOAuth2Provider commonProvider = CommonOAuth2Provider.valueOf(providerId.toUpperCase());
            return commonProvider.getBuilder(registrationId);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 从提供者属性获取构建器
     *
     * @param registrationId 注册ID
     * @param providerId     提供者ID
     * @return 构建器
     */
    private ClientRegistration.Builder getBuilderByProperties(String registrationId, String providerId) {
        // 获取提供者属性
        OAuth2ClientProperties.Provider provider = this.properties.getProvider().get(providerId);
        if (provider == null) {
            return null;
        }
        if (provider.getIssuerUri() != null) {
            // 从发行者位置获取构建器
            ClientRegistration.Builder builder = ClientRegistrations.fromIssuerLocation(provider.getIssuerUri())
                    .registrationId(registrationId);
            return copyProviderToBuilder(builder, provider);
        }
        // 从提供者属性获取构建器
        ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);
        return copyProviderToBuilder(builder, provider);
    }

    /**
     * 获取构建器
     *
     * @param builder  构建器
     * @param provider 提供者属性
     * @return 构建器
     */
    private ClientRegistration.Builder copyProviderToBuilder(ClientRegistration.Builder builder, OAuth2ClientProperties.Provider provider) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(provider::getAuthorizationUri).to(builder::authorizationUri);
        map.from(provider::getTokenUri).to(builder::tokenUri);
        map.from(provider::getUserInfoUri).to(builder::userInfoUri);
        map.from(provider::getUserInfoAuthenticationMethod)
                .as(AuthenticationMethod::new)
                .to(builder::userInfoAuthenticationMethod);
        map.from(provider::getJwkSetUri).to(builder::jwkSetUri);
        map.from(provider::getUserNameAttribute).to(builder::userNameAttributeName);
        return builder;
    }

}
