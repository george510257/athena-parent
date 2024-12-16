package com.gls.athena.common.bean.base;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.parser.NodeParser;

/**
 * 树节点解析器
 *
 * @param <T> 树节点类型
 * @author george
 */
public class ITreeNodeParser<T extends ITreeNode> implements NodeParser<T, Long> {
    /**
     * 解析
     *
     * @param object   源数据实体
     * @param treeNode 树节点实体
     */
    @Override
    public void parse(T object, Tree<Long> treeNode) {
        // 设置ID
        treeNode.setId(object.getId());
        // 设置父节点ID
        treeNode.setParentId(object.getParentId());
        // 设置节点名称
        treeNode.setName(object.getName());
        // 设置节点权重
        treeNode.setWeight(object.getSort());
        // 设置节点编码
        treeNode.putExtra("code", object.getCode());
        // 设置节点描述
        treeNode.putExtra("description", object.getDescription());
        // 设置节点类型
        treeNode.putExtra("type", object.getType());
        // 设置其它额外信息
        treeNode.putAll(BeanUtil.beanToMap(object));
    }
}
