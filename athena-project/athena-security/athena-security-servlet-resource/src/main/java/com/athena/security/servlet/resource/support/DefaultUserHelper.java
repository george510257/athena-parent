package com.athena.security.servlet.resource.support;

import cn.hutool.json.JSONUtil;
import com.athena.common.bean.security.IUser;
import com.athena.common.bean.security.IUserHelper;
import com.athena.common.bean.security.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author george
 */
@Slf4j
@Component
public class DefaultUserHelper implements IUserHelper {

    @Override
    public Optional<? extends IUser<?, ?, ?>> getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("principal: {}", JSONUtil.toJsonStr(principal));
        if (principal instanceof User user) {
            return Optional.of(user);
        }
        if (principal instanceof OAuth2AuthenticatedPrincipal oauth2Principal) {
            return Optional.of(toUser(oauth2Principal));
        }
        return Optional.empty();
    }

    /**
     * 转换为用户
     *
     * @param oauth2Principal OAuth2认证主体
     * @return 用户
     */
    private User toUser(OAuth2AuthenticatedPrincipal oauth2Principal) {
        User user = new User();
        user.setUsername(oauth2Principal.getName());
        user.setPassword(oauth2Principal.getAttribute("password"));
        user.setMobile(oauth2Principal.getAttribute("mobile"));
        user.setEmail(oauth2Principal.getAttribute("email"));
        user.setRealName(oauth2Principal.getAttribute("realName"));
        user.setNickName(oauth2Principal.getAttribute("nickName"));
        user.setAvatar(oauth2Principal.getAttribute("avatar"));
        user.setLanguage(oauth2Principal.getAttribute("language"));
        user.setLocale(oauth2Principal.getAttribute("locale"));
        user.setTimeZone(oauth2Principal.getAttribute("timeZone"));
        user.setRole(oauth2Principal.getAttribute("role"));
        user.setOrganization(oauth2Principal.getAttribute("organization"));
        user.setRoles(oauth2Principal.getAttribute("roles"));
        user.setOrganizations(oauth2Principal.getAttribute("organizations"));

        user.setId(oauth2Principal.getAttribute("id"));
        user.setTenantId(oauth2Principal.getAttribute("tenantId"));
        user.setVersion(oauth2Principal.getAttribute("version"));
        user.setDeleted(oauth2Principal.getAttribute("deleted"));
        user.setCreateTime(oauth2Principal.getAttribute("createTime"));
        user.setCreateUserId(oauth2Principal.getAttribute("createUserId"));
        user.setCreateUserName(oauth2Principal.getAttribute("createUserName"));
        user.setUpdateTime(oauth2Principal.getAttribute("updateTime"));
        user.setUpdateUserId(oauth2Principal.getAttribute("updateUserId"));
        user.setUpdateUserName(oauth2Principal.getAttribute("updateUserName"));
        return user;
    }
}
