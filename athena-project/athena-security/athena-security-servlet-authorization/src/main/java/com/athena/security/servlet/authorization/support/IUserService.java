package com.athena.security.servlet.authorization.support;

import com.athena.common.bean.security.IUserHelper;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.provisioning.UserDetailsManager;

/**
 * 用户服务
 */
public interface IUserService extends UserDetailsManager, UserDetailsPasswordService, IUserHelper {
}
