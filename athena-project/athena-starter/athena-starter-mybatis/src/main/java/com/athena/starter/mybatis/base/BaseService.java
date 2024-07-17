package com.athena.starter.mybatis.base;

import com.athena.common.bean.base.BaseVo;
import com.athena.common.bean.page.PageRequest;
import com.athena.common.bean.page.PageResponse;
import com.athena.common.core.base.IConverter;
import com.athena.common.core.base.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class BaseService<V extends BaseVo, E extends BaseEntity,
        C extends IConverter<V, E>, M extends IMapper<E>>
        extends ServiceImpl<M, E> implements IService<V> {

    @Autowired
    protected C converter;

    @Override
    public V insert(V vo) {
        E entity = converter.convert(vo);
        baseMapper.insert(entity);
        return converter.reverse(entity);
    }

    @Override
    public V update(V vo) {
        E entity = converter.convert(vo);
        baseMapper.updateById(entity);
        return converter.reverse(entity);
    }

    @Override
    public Boolean delete(Long id) {
        int count = baseMapper.deleteById(id);
        return count > 0;
    }

    @Override
    public V get(Long id) {
        return converter.reverse(baseMapper.selectById(id));
    }

    @Override
    public List<V> list(V vo) {
        return converter.reverseList(baseMapper.selectList(new QueryWrapper<>(converter.convert(vo))));
    }

    @Override
    public PageResponse<V> page(PageRequest<V> pageRequest) {
        return converter.reversePage(baseMapper.selectPage(converter.convertPage(pageRequest)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveBatch(List<V> vs) {
        return saveBatch(converter.convertList(vs));
    }
}
