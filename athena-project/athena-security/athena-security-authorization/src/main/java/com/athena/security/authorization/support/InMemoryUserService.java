package com.athena.security.authorization.support;

import cn.hutool.core.collection.CollUtil;
import com.athena.common.bean.security.IUser;
import com.athena.common.bean.security.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryUserService implements UserService {

    private static final List<User> USERS = new ArrayList<>();

    public InMemoryUserService() {
    }

    public InMemoryUserService(List<User> users) {
        USERS.addAll(users);
    }

    public InMemoryUserService(User... users) {
        CollUtil.addAll(USERS, users);
    }

    @Override
    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        return USERS.stream()
                .filter(user -> user.getMobile().equals(mobile))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
    }

    @Override
    public Optional<? extends IUser<?, ?, ?>> getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return USERS.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

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

    @Override
    public void createUser(UserDetails user) {
        if (userExists(user.getUsername())) {
            throw new IllegalArgumentException("用户已存在");
        }
        USERS.add((User) user);
    }

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

    @Override
    public void deleteUser(String username) {
        USERS.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .ifPresent(USERS::remove);
    }

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

    @Override
    public boolean userExists(String username) {
        return USERS.stream().anyMatch(user -> user.getUsername().equals(username));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return USERS.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
    }
}
