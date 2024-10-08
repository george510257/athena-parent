package com.athena.security.servlet.authorization.support;

import com.athena.common.bean.security.IUser;
import com.athena.common.bean.security.IUserHelper;
import com.athena.common.bean.security.User;
import com.athena.security.servlet.client.social.SocialUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.Optional;

/**
 * 用户服务
 *
 * @author george
 */
public interface IUserService extends UserDetailsManager, UserDetailsPasswordService, IUserHelper {

    /**
     * 获取当前用户
     *
     * @return 当前用户
     */
    @Override
    default Optional<? extends IUser<?, ?, ?>> getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 社交用户
        if (principal instanceof SocialUser socialUser) {
            return getUserByUsername(socialUser.getUsername());
        }
        // 用户
        if (principal instanceof User user) {
            return Optional.of(user);
        }
        // OAuth2用户
        if (principal instanceof OAuth2AuthenticatedPrincipal oauth2Principal) {
            return Optional.of(IUserHelper.toUser(oauth2Principal));
        }
        // 未知用户
        return Optional.empty();
    }

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return 用户
     */
    default Optional<User> getUserByUsername(String username) {
        try {
            return Optional.of((User) loadUserByUsername(username));
        } catch (UsernameNotFoundException e) {
            return Optional.empty();
        }
    }
}
