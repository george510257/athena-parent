package com.athena.security.authorization.support;

import cn.hutool.core.collection.CollUtil;
import com.athena.common.bean.security.IUser;
import com.athena.common.bean.security.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryUserService implements UserService {

    private final List<User> users = new ArrayList<>();

    public InMemoryUserService() {
    }

    public InMemoryUserService(List<User> users) {
        this.users.addAll(users);
    }

    public InMemoryUserService(User... users) {
        CollUtil.addAll(this.users, users);
    }


    @Override
    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        return users.stream()
                .filter(user -> user.getMobile().equals(mobile))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
    }

    @Override
    public Optional<? extends IUser<?, ?, ?>> getCurrentUser() {
        return Optional.empty();
    }

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return users.stream()
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
        users.add((User) user);
    }

    @Override
    public void updateUser(UserDetails user) {
        users.stream()
                .filter(u -> u.getUsername().equals(user.getUsername()))
                .findFirst()
                .ifPresent(u -> {
                    users.remove(u);
                    users.add((User) user);
                });
    }

    @Override
    public void deleteUser(String username) {
        users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .ifPresent(users::remove);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        User user = (User) getCurrentUser().orElseThrow(() -> new IllegalArgumentException("用户未登录"));
        if (!user.getPassword().equals(oldPassword)) {
            throw new IllegalArgumentException("原密码错误");
        }
        users.stream()
                .filter(u -> u.getUsername().equals(user.getUsername()))
                .findFirst()
                .ifPresent(u -> u.setPassword(newPassword));
    }

    @Override
    public boolean userExists(String username) {
        return users.stream().anyMatch(user -> user.getUsername().equals(username));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
    }
}
