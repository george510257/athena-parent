package com.athena.security.servlet.client.social;

/**
 * 社交用户服务
 *
 * @author george
 */
public interface SocialUserService {

    /**
     * 根据社交平台id和社交平台用户id查询社交用户
     *
     * @param registrationId 社交平台id
     * @param providerUserId 社交平台用户id
     * @return 社交用户
     */
    SocialUser getSocialUser(String registrationId, String providerUserId);
}
