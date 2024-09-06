package com.athena.security.servlet.client.social;

import com.athena.common.bean.base.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Delegate;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * 社交用户
 *
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SocialUser extends BaseVo implements OAuth2User {

    @Delegate
    private OAuth2User oauth2User;
    /**
     * 社交平台id
     */
    private String providerId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 绑定状态 true 已绑定 false 未绑定
     */
    private Boolean bindStatus;

}
