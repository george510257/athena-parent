package com.athena.security.servlet.client.social;

import cn.hutool.core.collection.CollUtil;

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
     * 构造方法
     *
     * @param users 用户列表
     */
    public InMemorySocialUserService(SocialUser... users) {
        CollUtil.addAll(USERS, users);
    }

    /**
     * 根据社交平台id和社交平台用户id查询社交用户
     *
     * @param registrationId 社交平台id
     * @param providerUserId 社交平台用户id
     * @return 社交用户
     */
    @Override
    public SocialUser getSocialUser(String registrationId, String providerUserId) {
        return USERS.stream()
                .filter(user -> user.getRegistrationId().equals(registrationId) && user.getProviderUserId().equals(providerUserId))
                .findFirst()
                .orElse(null);
    }
}
