package com.athena.common.bean.security;

import com.athena.common.bean.base.IDomain;
import com.athena.common.bean.base.ITreeNode;

/**
 * 权限接口
 */
public interface IPermission extends ITreeNode, IDomain {
    /**
     * 获取权限指令
     *
     * @return 权限指令
     */
    String getCommand();

    /**
     * 设置权限指令
     *
     * @param command 权限指令
     */
    void setCommand(String command);
}