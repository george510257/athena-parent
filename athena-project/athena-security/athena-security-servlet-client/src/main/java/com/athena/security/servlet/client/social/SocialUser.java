package com.athena.security.servlet.client.social;

import com.athena.common.bean.base.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 社交用户
 *
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SocialUser extends BaseVo {
    /**
     * 社交平台id
     */
    private String registrationId;
    /**
     * 社交平台用户id
     */
    private String providerUserId;
    /**
     * 用户名
     */
    private String username;

}
