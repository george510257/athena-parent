package com.athena.common.bean.security;

import com.athena.common.bean.base.IDomain;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 用户接口
 *
 * @param <R> 角色
 * @param <P> 权限
 * @param <O> 组织机构
 */
public interface IUser<R extends IRole<P>, P extends IPermission, O extends IOrganization> extends UserDetails, IDomain {
    /**
     * 获取手机号
     *
     * @return 手机号
     */
    String getMobile();

    /**
     * 获取邮箱
     *
     * @return 邮箱
     */
    String getEmail();

    /**
     * 获取姓名
     *
     * @return 姓名
     */
    String getRealName();

    /**
     * 获取昵称
     *
     * @return 昵称
     */
    String getNickName();

    /**
     * 获取用户头像
     *
     * @return 头像
     */
    String getAvatar();

    /**
     * 获取用户语言
     *
     * @return 语言
     */
    String getLanguage();

    /**
     * 获取用户区域
     *
     * @return 区域
     */
    String getLocale();

    /**
     * 获取用户时区
     *
     * @return 时区
     */
    String getTimeZone();

    /**
     * 获取当前角色
     *
     * @return 角色
     */
    R getRole();

    /**
     * 获取当前组织机构
     *
     * @return 组织机构
     */
    O getOrganization();

    /**
     * 获取角色列表
     *
     * @return 角色列表
     */
    List<R> getRoles();

    /**
     * 获取组织机构列表
     *
     * @return 组织机构列表
     */
    List<O> getOrganizations();

    /**
     * 获取权限列表
     *
     * @return 权限列表
     */
    @Override
    default Collection<? extends IRole<P>> getAuthorities() {
        return this.getRoles();
    }

}
