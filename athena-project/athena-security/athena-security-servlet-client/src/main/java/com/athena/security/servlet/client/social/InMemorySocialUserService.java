package com.athena.security.servlet.client.social;

import java.util.ArrayList;
import java.util.List;

/**
 * 社交用户服务
 *
 * @author george
 */
public class InMemorySocialUserService implements SocialUserService {

    /**
     * 用户列表
     */
    private static final List<SocialUser> USERS = new ArrayList<>();

    /**
     * 根据社交平台id和社交平台用户id查询社交用户
     *
     * @param providerId 社交平台id
     * @param name       社交平台用户id
     * @return 社交用户
     */
    @Override
    public SocialUser loadSocialUser(String providerId, String name) {
        return USERS.stream()
                .filter(user -> user.getProviderId().equals(providerId) && user.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * 保存社交用户
     *
     * @param socialUser 社交用户
     */
    @Override
    public void saveSocialUser(SocialUser socialUser) {
        USERS.stream()
                .filter(user -> user.getProviderId().equals(socialUser.getProviderId()) && user.getName().equals(socialUser.getName()))
                .findFirst()
                .ifPresent(USERS::remove);
        USERS.add(socialUser);
    }

    /**
     * 删除社交用户
     *
     * @param providerId 社交平台id
     * @param name       社交平台用户id
     */
    @Override
    public void removeSocialUser(String providerId, String name) {
        USERS.removeIf(user -> user.getProviderId().equals(providerId) && user.getName().equals(name));
    }
}
