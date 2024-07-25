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

/**
 * 基础服务类
 *
 * @param <V> 视图对象
 * @param <E> 实体对象
 * @param <C> 转换器
 * @param <M> Mapper
 */
public abstract class BaseService<V extends BaseVo, E extends BaseEntity,
        C extends IConverter<V, E>, M extends IMapper<E>>
        extends ServiceImpl<M, E> implements IService<V> {

    @Autowired
    protected C converter;

    @Override
    public V insert(V vo) {
        E entity = converter.convert(vo);
        save(entity);
        return converter.reverse(entity);
    }

    @Override
    public V update(V vo) {
        E entity = converter.convert(vo);
        updateById(entity);
        return converter.reverse(entity);
    }

    @Override
    public Boolean delete(Long id) {
        return removeById(id);
    }

    @Override
    public V get(Long id) {
        return converter.reverse(getById(id));
    }

    @Override
    public List<V> list(V vo) {
        return converter.reverseList(list(new QueryWrapper<>(converter.convert(vo))));
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
