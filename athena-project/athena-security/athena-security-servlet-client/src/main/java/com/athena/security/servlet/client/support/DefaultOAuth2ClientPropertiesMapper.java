package com.athena.security.servlet.client.support;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrations;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 默认OAuth2客户端属性映射器
 *
 * @author george
 */
@RequiredArgsConstructor
public class DefaultOAuth2ClientPropertiesMapper {
    /**
     * OAuth2客户端属性
     */
    private final OAuth2ClientProperties properties;

    /**
     * 获取客户端注册信息列表
     *
     * @return 客户端注册信息列表
     */
    public List<ClientRegistration> getClientRegistrationsList() {
        return new ArrayList<>(getClientRegistrationsMap().values());
    }

    /**
     * 获取客户端注册信息Map
     *
     * @return 客户端注册信息Map
     */
    private Map<String, ClientRegistration> getClientRegistrationsMap() {
        return this.properties.getRegistration().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry ->
                        toClientRegistration(entry.getKey(), entry.getValue())));
    }

    /**
     * 转换为客户端注册信息
     *
     * @param registrationId 注册ID
     * @param registration   注册信息
     * @return 客户端注册信息
     */
    private ClientRegistration toClientRegistration(String registrationId,
                                                    OAuth2ClientProperties.Registration registration) {
        // 获取构建器
        ClientRegistration.Builder builder = getBuilder(registrationId, registration);
        // 映射属性
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
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
     * @param registration   注册信息
     * @return 构建器
     */
    private ClientRegistration.Builder getBuilder(String registrationId,
                                                  OAuth2ClientProperties.Registration registration) {
        // 获取提供者ID
        String providerId = registration.getProvider() != null ? registration.getProvider() : registrationId;
        // 获取构建器 - 从提供者属性
        ClientRegistration.Builder builder = getBuilderByProviderProperties(registrationId, providerId);
        if (builder == null) {
            // 获取构建器 - 从通用提供者
            builder = getBuilderByCommonProvider(registrationId, providerId);
        }
        if (builder == null) {
            // 获取构建器 - 从默认提供者
            builder = getBuilderByDefaultProvider(registrationId, providerId);
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
    private ClientRegistration.Builder getBuilderByDefaultProvider(String registrationId, String providerId) {
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
    private ClientRegistration.Builder getBuilderByCommonProvider(String registrationId, String providerId) {
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
    private ClientRegistration.Builder getBuilderByProviderProperties(String registrationId, String providerId) {
        // 获取提供者属性
        OAuth2ClientProperties.Provider provider = this.properties.getProvider().get(providerId);
        if (provider == null) {
            return null;
        }
        if (provider.getIssuerUri() != null) {
            // 从发行者位置获取构建器
            ClientRegistration.Builder builder = ClientRegistrations.fromIssuerLocation(provider.getIssuerUri())
                    .registrationId(registrationId);
            return getBuilder(builder, provider);
        }
        // 从提供者属性获取构建器
        ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);
        return getBuilder(builder, provider);
    }

    /**
     * 获取构建器
     *
     * @param builder  构建器
     * @param provider 提供者属性
     * @return 构建器
     */
    private ClientRegistration.Builder getBuilder(ClientRegistration.Builder builder, OAuth2ClientProperties.Provider provider) {
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
