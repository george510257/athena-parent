package com.athena.common.bean.base;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.parser.NodeParser;

public class ITreeNodeParser<T extends ITreeNode> implements NodeParser<T, Long> {
    @Override
    public void parse(T object, Tree<Long> treeNode) {
        treeNode.setId(object.getId());
        treeNode.setParentId(object.getParentId());
        treeNode.setName(object.getName());
        treeNode.setWeight(object.getSort());
        treeNode.putExtra("code", object.getCode());
        treeNode.putExtra("description", object.getDescription());
        treeNode.putExtra("type", object.getType());
        treeNode.putAll(BeanUtil.beanToMap(object));
    }
}
