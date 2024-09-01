package com.athena.common.bean.base;

import java.io.Serializable;

/**
 * 树节点接口
 *
 * @author george
 */
public interface ITreeNode extends Serializable {
    /**
     * 获取节点ID
     *
     * @return 节点ID
     */
    Long getId();

    /**
     * 设置节点ID
     *
     * @param id 节点ID
     */
    void setId(Long id);

    /**
     * 获取父节点ID
     *
     * @return 父节点ID
     */
    Long getParentId();

    /**
     * 设置父节点ID
     *
     * @param parentId 父节点ID
     */
    void setParentId(Long parentId);

    /**
     * 获取节点名称
     *
     * @return 节点名称
     */
    String getName();

    /**
     * 设置节点名称
     *
     * @param name 节点名称
     */
    void setName(String name);

    /**
     * 获取节点编码
     *
     * @return 节点编码
     */
    String getCode();

    /**
     * 设置节点编码
     *
     * @param code 节点编码
     */
    void setCode(String code);

    /**
     * 获取节点类型
     *
     * @return 节点类型
     */
    String getType();

    /**
     * 设置节点类型
     *
     * @param type 节点类型
     */
    void setType(String type);

    /**
     * 获取节点描述
     *
     * @return 节点描述
     */
    String getDescription();

    /**
     * 设置节点描述
     *
     * @param description 节点描述
     */
    void setDescription(String description);

    /**
     * 获取节点排序
     *
     * @return 节点排序
     */
    Integer getSort();

    /**
     * 设置节点排序
     *
     * @param sort 节点排序
     */
    void setSort(Integer sort);
}
