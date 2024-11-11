package com.gls.athena.common.bean.security;

import com.gls.athena.common.bean.base.IDomain;
import com.gls.athena.common.bean.base.ITreeNode;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * 角色信息
 *
 * @param <P> 权限类型
 * @author george
 */
public interface IRole<P extends IPermission> extends GrantedAuthority, ITreeNode, IDomain {
    /**
     * 获取角色编码
     *
     * @return 角色编码
     */
    @Override
    default String getAuthority() {
        return this.getCode();
    }

    /**
     * 获取权限列表
     *
     * @return 权限列表
     */
    List<P> getPermissions();

    /**
     * 设置权限列表
     *
     * @param permissions 权限列表
     */
    void setPermissions(List<P> permissions);
}
