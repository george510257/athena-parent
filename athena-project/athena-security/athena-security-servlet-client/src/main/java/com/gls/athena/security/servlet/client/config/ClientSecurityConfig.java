package com.gls.athena.security.servlet.client.config;

import com.gls.athena.security.servlet.client.support.DefaultOAuth2ClientPropertiesMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

/**
 * 客户端安全配置
 *
 * @author george
 */
@Configuration
public class ClientSecurityConfig {

    /**
     * 客户端注册信息
     *
     * @param properties OAuth2客户端属性
     * @return 客户端注册信息
     */
    @Bean
    @ConditionalOnProperty(prefix = "athena.security.client", name = "type", havingValue = "IN_MEMORY", matchIfMissing = true)
    public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties properties) {
        // 创建内存客户端注册信息库
        return new InMemoryClientRegistrationRepository(new DefaultOAuth2ClientPropertiesMapper(properties).getClientRegistrations());
    }

}
