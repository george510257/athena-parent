package com.gls.athena.starter.data.jpa.support;

import com.gls.athena.common.bean.page.PageRequest;
import com.gls.athena.common.bean.page.PageResponse;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 分页工具类
 *
 * @author george
 */
@UtilityClass
public class PageUtil {
    /**
     * 获取分页参数
     *
     * @param pageRequest 分页查询
     * @return 分页参数
     */
    public Pageable toPageable(PageRequest<?> pageRequest) {
        String order = pageRequest.getOrder();
        if ("desc".equalsIgnoreCase(order)) {
            return org.springframework.data.domain.PageRequest.of(pageRequest.getPage(), pageRequest.getSize(), Sort.Direction.DESC, pageRequest.getSort());
        }
        if ("asc".equalsIgnoreCase(order)) {
            return org.springframework.data.domain.PageRequest.of(pageRequest.getPage(), pageRequest.getSize(), Sort.Direction.ASC, pageRequest.getSort());
        }
        return org.springframework.data.domain.PageRequest.of(pageRequest.getPage(), pageRequest.getSize());
    }

    /**
     * 获取分页结果
     *
     * @param page 分页数据
     * @param <E>  实体类型
     * @return 分页结果
     */
    public <E> PageResponse<E> toPageResponse(Page<E> page) {
        return new PageResponse<E>()
                .setPage(page.getNumber())
                .setSize(page.getSize())
                .setTotal(page.getTotalElements())
                .setPages(page.getTotalPages())
                .setData(page.getContent());
    }
}
