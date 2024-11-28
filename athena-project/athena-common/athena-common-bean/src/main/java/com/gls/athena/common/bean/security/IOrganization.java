package com.gls.athena.common.bean.security;

import com.gls.athena.common.bean.base.IDomain;
import com.gls.athena.common.bean.base.ITreeNode;

/**
 * 组织机构接口
 *
 * @author george
 */
public interface IOrganization extends ITreeNode, IDomain {
    /**
     * 是否默认组织
     *
     * @return 是否默认组织
     */
    Boolean getDefaultOrganization();
}
