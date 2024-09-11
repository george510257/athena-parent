package com.athena.security.servlet.authorization.support;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.athena.common.bean.security.IUser;
import com.athena.common.bean.security.User;
import com.athena.security.servlet.client.social.SocialUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 内存用户服务
 *
 * @author george
 */
@Slf4j
public class InMemoryUserService implements IUserService {

    /**
     * 用户列表
     */
    private static final List<User> USERS = new ArrayList<>();

    /**
     * 构造方法
     *
     * @param users 用户列表
     */
    public InMemoryUserService(User... users) {
        CollUtil.addAll(USERS, users);
    }

    /**
     * 获取当前用户
     *
     * @return 当前用户
     */
    @Override
    public Optional<? extends IUser<?, ?, ?>> getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("principal: {}", JSONUtil.toJsonStr(principal));
        if (principal instanceof User user) {
            return Optional.of(user);
        }
        if (principal instanceof SocialUser socialUser) {
            return USERS.stream()
                    .filter(u -> u.getUsername().equals(socialUser.getUsername()))
                    .findFirst();
        }
        if (principal instanceof DefaultOAuth2AuthenticatedPrincipal oauth2Principal) {
            return USERS.stream()
                    .filter(u -> u.getUsername().equals(oauth2Principal.getName()))
                    .findFirst();
        }
        return Optional.empty();
    }

    /**
     * 更新密码
     *
     * @param user        用户
     * @param newPassword 新密码
     *                    {@code PasswordEncoder} 加密后的密码
     * @return 用户
     */
    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return USERS.stream()
                .filter(u -> u.getUsername().equals(user.getUsername()))
                .findFirst()
                .map(u -> {
                    u.setPassword(newPassword);
                    return u;
                })
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
    }

    /**
     * 创建用户
     *
     * @param user 用户
     */
    @Override
    public void createUser(UserDetails user) {
        if (userExists(user.getUsername())) {
            throw new IllegalArgumentException("用户已存在");
        }
        USERS.add((User) user);
    }

    /**
     * 更新用户
     *
     * @param user 用户
     */
    @Override
    public void updateUser(UserDetails user) {
        USERS.stream()
                .filter(u -> u.getUsername().equals(user.getUsername()))
                .findFirst()
                .ifPresent(u -> {
                    USERS.remove(u);
                    USERS.add((User) user);
                });
    }

    /**
     * 删除用户
     *
     * @param username 用户名
     */
    @Override
    public void deleteUser(String username) {
        USERS.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .ifPresent(USERS::remove);
    }

    /**
     * 修改密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    @Override
    public void changePassword(String oldPassword, String newPassword) {
        User user = (User) getCurrentUser().orElseThrow(() -> new IllegalArgumentException("用户未登录"));
        if (!user.getPassword().equals(oldPassword)) {
            throw new IllegalArgumentException("原密码错误");
        }
        USERS.stream()
                .filter(u -> u.getUsername().equals(user.getUsername()))
                .findFirst()
                .ifPresent(u -> u.setPassword(newPassword));
    }

    /**
     * 用户是否存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    @Override
    public boolean userExists(String username) {
        return USERS.stream().anyMatch(user -> user.getUsername().equals(username));
    }

    /**
     * 通过用户名加载用户
     *
     * @param username 用户名
     * @return 用户
     * @throws UsernameNotFoundException 用户不存在
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return USERS.stream()
                .filter(user -> user.getUsername().equals(username) || user.getMobile().equals(username))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
    }
}
