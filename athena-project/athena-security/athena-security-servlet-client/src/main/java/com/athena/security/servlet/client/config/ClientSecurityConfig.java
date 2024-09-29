package com.athena.security.servlet.client.config;

import com.athena.security.servlet.client.support.DefaultOAuth2ClientPropertiesMapper;
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
     * @param mapper oauth2客户端属性
     * @return 客户端注册信息
     */
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(DefaultOAuth2ClientPropertiesMapper mapper) {
        // 创建内存客户端注册信息库
        return new InMemoryClientRegistrationRepository(mapper.getClientRegistrations());
    }

}
