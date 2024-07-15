package com.athena.starter.mybatis.base;

import com.athena.common.bean.page.PageRequest;
import com.athena.common.bean.page.PageResponse;
import com.athena.starter.mybatis.support.PageUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * Mapper接口
 *
 * @param <E> 实体类型
 */
public interface IMapper<E> extends BaseMapper<E> {

    /**
     * 分页查询
     *
     * @param pageRequest 分页查询
     * @return 分页结果
     */
    default PageResponse<E> selectPage(PageRequest<E> pageRequest) {
        IPage<E> page = PageUtil.toPage(pageRequest);
        IPage<E> result = selectPage(page, new QueryWrapper<>(pageRequest.getParams()));
        return PageUtil.toPageResponse(result);
    }
}
