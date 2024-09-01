package com.athena.security.servlet.client.config;

import com.athena.security.servlet.client.support.DefaultOAuth2ClientPropertiesMapper;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import java.util.List;

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
     * @param oauth2ClientProperties oauth2客户端属性
     * @return 客户端注册信息
     */
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties oauth2ClientProperties) {
        List<ClientRegistration> clientRegistrations = new DefaultOAuth2ClientPropertiesMapper(oauth2ClientProperties).getClientRegistrationsList();
        return new InMemoryClientRegistrationRepository(clientRegistrations);
    }

}
