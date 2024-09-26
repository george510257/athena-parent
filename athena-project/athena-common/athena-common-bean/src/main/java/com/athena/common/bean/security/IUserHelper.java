package com.athena.common.bean.security;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import com.athena.common.bean.base.ITreeNodeParser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

/**
 * 用户帮助类
 *
 * @author george
 */
public interface IUserHelper {

    /**
     * 默认用户帮助类
     *
     * @return 默认用户帮助类
     */
    static IUserHelper withDefaults() {
        return () -> {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            // 普通用户
            if (principal instanceof User user) {
                return Optional.of(user);
            }
            // token用户
            if (principal instanceof OAuth2AuthenticatedPrincipal oauth2Principal) {
                return Optional.of(toUser(oauth2Principal));
            }
            return Optional.empty();
        };
    }

    /**
     * 转换为用户
     *
     * @param oauth2Principal OAuth2认证主体
     * @return 用户
     */
    static User toUser(OAuth2AuthenticatedPrincipal oauth2Principal) {
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

    /**
     * 获取当前用户
     *
     * @return 当前用户
     */
    Optional<? extends IUser<?, ?, ?>> getCurrentUser();

    /**
     * 获取当前用户ID
     *
     * @return 当前用户ID
     */
    default Optional<Long> getCurrentUserId() {
        return this.getCurrentUser().map(IUser::getId);
    }

    /**
     * 获取当前用户租户ID
     *
     * @return 当前用户租户ID
     */
    default Optional<Long> getCurrentUserTenantId() {
        return this.getCurrentUser().map(IUser::getTenantId);
    }

    /**
     * 获取当前用户用户名
     *
     * @return 当前用户用户名
     */
    default Optional<String> getCurrentUsername() {
        return this.getCurrentUser().map(IUser::getUsername);
    }

    /**
     * 获取当前用户手机号
     *
     * @return 当前用户手机号
     */
    default Optional<String> getCurrentUserMobile() {
        return this.getCurrentUser().map(IUser::getMobile);
    }

    /**
     * 获取当前用户邮箱
     *
     * @return 当前用户邮箱
     */
    default Optional<String> getCurrentUserEmail() {
        return this.getCurrentUser().map(IUser::getEmail);
    }

    /**
     * 获取当前用户姓名
     *
     * @return 当前用户姓名
     */
    default Optional<String> getCurrentUserRealName() {
        return this.getCurrentUser().map(IUser::getRealName);
    }

    /**
     * 获取当前用户昵称
     *
     * @return 当前用户昵称
     */
    default Optional<String> getCurrentUserNickName() {
        return this.getCurrentUser().map(IUser::getNickName);
    }

    /**
     * 获取当前用户头像
     *
     * @return 当前用户头像
     */
    default Optional<String> getCurrentUserAvatar() {
        return this.getCurrentUser().map(IUser::getAvatar);
    }

    /**
     * 获取当前用户语言
     *
     * @return 当前用户语言
     */
    default Optional<String> getCurrentUserLanguage() {
        return this.getCurrentUser().map(IUser::getLanguage);
    }

    /**
     * 获取当前用户区域
     *
     * @return 当前用户区域
     */
    default Optional<Locale> getCurrentUserLocale() {
        return this.getCurrentUser().map(IUser::getLocale).map(Locale::forLanguageTag);
    }

    /**
     * 获取当前用户时区
     *
     * @return 当前用户时区
     */
    default Optional<TimeZone> getCurrentUserTimeZone() {
        return this.getCurrentUser().map(IUser::getTimeZone).map(TimeZone::getTimeZone);
    }

    /**
     * 获取当前用户角色
     *
     * @return 当前用户角色
     */
    default Optional<? extends IRole<?>> getCurrentUserRole() {
        return this.getCurrentUser().map(IUser::getRole);
    }

    /**
     * 获取当前用户组织机构
     *
     * @return 当前用户组织机构
     */
    default Optional<? extends IOrganization> getCurrentUserOrganization() {
        return this.getCurrentUser().map(IUser::getOrganization);
    }

    /**
     * 获取当前用户角色列表
     *
     * @return 当前用户角色列表
     */
    default Optional<List<? extends IRole<?>>> getCurrentUserRoles() {
        return this.getCurrentUser().map(IUser::getRoles);
    }

    /**
     * 获取当前用户组织机构列表
     *
     * @return 当前用户组织机构列表
     */
    default Optional<List<? extends IOrganization>> getCurrentUserOrganizations() {
        return this.getCurrentUser().map(IUser::getOrganizations);
    }

    /**
     * 获取当前用户权限列表
     *
     * @return 当前用户权限列表
     */
    default Optional<List<? extends IPermission>> getCurrentUserPermissions() {
        return this.getCurrentUser()
                .map(IUser::getRoles)
                .map(roles -> roles.stream()
                        .map(IRole::getPermissions)
                        .flatMap(List::stream)
                        .distinct().toList());
    }

    /**
     * 获取当前用户角色树
     *
     * @return 当前用户角色树
     */
    default Optional<List<Tree<Long>>> getCurrentUserRoleTree() {
        return this.getCurrentUserRoles()
                .map(roles -> TreeUtil.build(roles, 0L, new ITreeNodeParser<>()));
    }

    /**
     * 获取当前用户组织机构树
     *
     * @return 当前用户组织机构树
     */
    default Optional<List<Tree<Long>>> getCurrentUserOrganizationTree() {
        return this.getCurrentUserOrganizations()
                .map(organizations -> TreeUtil.build(organizations, 0L, new ITreeNodeParser<>()));
    }

    /**
     * 获取当前用户权限树
     *
     * @return 当前用户权限树
     */
    default Optional<List<Tree<Long>>> getCurrentUserPermissionTree() {
        return this.getCurrentUserPermissions()
                .map(permissions -> TreeUtil.build(permissions, 0L, new ITreeNodeParser<>()));
    }

}
