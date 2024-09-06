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
     * @param providerId 社交平台id
     * @param name       社交平台用户id
     * @return 社交用户
     */
    SocialUser loadSocialUser(String providerId, String name);

    /**
     * 保存社交用户
     *
     * @param socialUser 社交用户
     */
    void saveSocialUser(SocialUser socialUser);

    /**
     * 删除社交用户
     *
     * @param providerId 社交平台id
     * @param name       社交平台用户id
     */
    void removeSocialUser(String providerId, String name);
}
