package com.gls.athena.starter.mybatis.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gls.athena.common.bean.base.BaseVo;
import com.gls.athena.common.bean.page.PageRequest;
import com.gls.athena.common.bean.page.PageResponse;
import com.gls.athena.common.core.base.IConverter;
import com.gls.athena.common.core.base.IService;
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
 * @author george
 */
public abstract class BaseService<V extends BaseVo, E extends BaseEntity,
        C extends IConverter<V, E>, M extends IMapper<E>>
        extends ServiceImpl<M, E> implements IService<V> {
    /**
     * 转换器
     */
    @Autowired
    protected C converter;

    /**
     * 插入
     *
     * @param vo VO 对象
     * @return VO 对象
     */
    @Override
    public V insert(V vo) {
        E entity = converter.convert(vo);
        save(entity);
        return converter.reverse(entity);
    }

    /**
     * 更新
     *
     * @param vo VO 对象
     * @return VO 对象
     */
    @Override
    public V update(V vo) {
        E entity = converter.convert(vo);
        updateById(entity);
        return converter.reverse(entity);
    }

    /**
     * 删除
     *
     * @param id ID 主键
     * @return 是否成功
     */
    @Override
    public Boolean delete(Long id) {
        return removeById(id);
    }

    /**
     * 获取
     *
     * @param id ID 主键
     * @return VO 对象
     */
    @Override
    public V get(Long id) {
        return converter.reverse(getById(id));
    }

    /**
     * 列表
     *
     * @param vo VO 对象
     * @return VO 对象列表
     */
    @Override
    public List<V> list(V vo) {
        return converter.reverseList(list(new QueryWrapper<>(converter.convert(vo))));
    }

    /**
     * 分页
     *
     * @param pageRequest 分页请求
     * @return 分页响应
     */
    @Override
    public PageResponse<V> page(PageRequest<V> pageRequest) {
        return converter.reversePage(baseMapper.selectPage(converter.convertPage(pageRequest)));
    }

    /**
     * 批量插入
     *
     * @param vs VO列表
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveBatch(List<V> vs) {
        return saveBatch(converter.convertList(vs));
    }
}
