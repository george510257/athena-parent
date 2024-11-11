package com.gls.athena.common.core.base;

import com.gls.athena.common.bean.base.BaseVo;
import com.gls.athena.common.bean.page.PageRequest;
import com.gls.athena.common.bean.page.PageResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * 服务接口
 *
 * @param <Vo> VO
 * @author george
 */
public interface IService<Vo extends BaseVo> {

    /**
     * 新增
     *
     * @param vo VO
     * @return VO
     */
    @CachePut(key = "#result.id")
    Vo insert(Vo vo);

    /**
     * 修改
     *
     * @param vo VO
     * @return VO
     */
    @CachePut(key = "#result.id")
    Vo update(Vo vo);

    /**
     * 删除
     *
     * @param id ID
     * @return 是否成功
     */
    @CacheEvict(key = "#id")
    Boolean delete(Long id);

    /**
     * 查询
     *
     * @param id ID
     * @return VO
     */
    @Cacheable(key = "#id")
    Vo get(Long id);

    /**
     * 查询列表
     *
     * @param vo VO
     * @return VO列表
     */
    List<Vo> list(Vo vo);

    /**
     * 分页查询
     *
     * @param pageRequest 分页请求
     * @return 分页VO
     */
    PageResponse<Vo> page(PageRequest<Vo> pageRequest);

    /**
     * 批量新增&修改
     *
     * @param voList VO列表
     * @return 是否成功
     */
    Boolean saveBatch(List<Vo> voList);
}
