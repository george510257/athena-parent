package com.athena.security.servlet.client.social;

import com.athena.common.bean.security.User;
import com.athena.security.servlet.client.config.ClientSecurityConstants;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * 社交用户绑定监听器
 *
 * @author george
 */
@Slf4j
@Component
public class SocialUserBindListener implements ApplicationListener<AuthenticationSuccessEvent> {
    @Resource
    private ISocialUserService socialUserService;
    @Resource
    private HttpSession session;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        log.info("社交用户绑定监听器");
        Authentication authentication = event.getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            User user = (User) authentication.getPrincipal();
            SocialUser socialUser = (SocialUser) session.getAttribute(ClientSecurityConstants.SOCIAL_USER_SESSION_KEY);
            if (socialUser != null) {
                // 绑定社交用户
                socialUser.setUsername(user.getUsername());
                socialUser.setBindStatus(true);
                socialUserService.saveSocialUser(socialUser);
                session.removeAttribute(ClientSecurityConstants.SOCIAL_USER_SESSION_KEY);
                log.info("社交用户绑定成功");
            }
        }
    }
}
