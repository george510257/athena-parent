package com.athena.security.authorization.support;

import com.athena.common.bean.security.IUserHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.provisioning.UserDetailsManager;

public interface IUserService extends UserDetailsManager, UserDetailsPasswordService, IUserHelper {

    UserDetails loadUserByMobile(String mobile);
}
