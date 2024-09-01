package com.athena.starter.data.jpa.base;

import com.athena.common.bean.base.BaseVo;
import com.athena.common.bean.page.PageRequest;
import com.athena.common.bean.page.PageResponse;
import com.athena.common.core.base.IConverter;
import com.athena.common.core.base.IService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 基础服务
 *
 * @param <V> 视图
 * @param <E> 实体
 * @param <C> 转换器
 * @param <R> 仓库
 * @author george
 */
public abstract class BaseService<V extends BaseVo, E extends BaseEntity,
        C extends IConverter<V, E>, R extends IRepository<E>> implements IService<V> {

    /**
     * 转换器
     */
    @Autowired
    protected C converter;

    /**
     * 仓库
     */
    @Autowired
    protected R repository;

    /**
     * 新增
     *
     * @param vo VO 对象
     * @return VO 对象
     */
    @Override
    public V insert(V vo) {
        return converter.reverse(repository.save(converter.convert(vo)));
    }

    /**
     * 修改
     *
     * @param vo VO 对象
     * @return VO 对象
     */
    @Override
    public V update(V vo) {
        if (vo == null || vo.getId() == null) {
            throw new IllegalArgumentException("id不能为空");
        }
        return converter.reverse(repository.save(converter.convert(vo)));
    }

    /**
     * 删除
     *
     * @param id ID
     * @return 是否成功
     */
    @Override
    public Boolean delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id不能为空");
        }
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * 查询
     *
     * @param id ID 主键
     * @return VO 对象
     */
    @Override
    public V get(Long id) {
        return repository.findById(id).map(converter::reverse).orElse(null);
    }

    /**
     * 查询
     *
     * @param vo VO 对象
     * @return VO 对象
     */
    @Override
    public List<V> list(V vo) {
        return converter.reverseList(repository.findAll(converter.convert(vo)));
    }

    /**
     * 分页查询
     *
     * @param pageRequest 分页请求
     * @return 分页数据
     */
    @Override
    public PageResponse<V> page(PageRequest<V> pageRequest) {
        return converter.reversePage(repository.findAll(converter.convertPage(pageRequest)));
    }

    /**
     * 批量保存
     *
     * @param vs VO列表
     * @return 是否成功
     */
    @Override
    public Boolean saveBatch(List<V> vs) {
        return repository.saveAll(converter.convertList(vs)).size() == vs.size();
    }
}
