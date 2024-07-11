package com.athena.starter.data.jpa.base;

import com.athena.common.bean.page.PageRequest;
import com.athena.common.bean.page.PageResponse;
import com.athena.starter.data.jpa.support.PageUtil;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface IRepository<E extends BaseEntity> extends JpaRepositoryImplementation<E, Long> {

    /**
     * 获取翻页数据
     *
     * @param entity   查询条件
     * @param pageable 分页参数
     * @return 翻页数据
     */
    default Page<E> findAll(E entity, Pageable pageable) {
        return findAll(Example.of(entity), pageable);
    }

    /**
     * 获取数据列表
     *
     * @param entity 查询条件
     * @return 数据列表
     */
    default List<E> findAll(E entity) {
        return findAll(Example.of(entity));
    }

    /**
     * 分页查询
     *
     * @param pageRequest 分页请求
     * @return 分页数据
     */
    default PageResponse<E> findAll(PageRequest<E> pageRequest) {
        Pageable pageable = PageUtil.toPageable(pageRequest);
        Page<E> page = findAll(pageRequest.getParams(), pageable);
        return PageUtil.toPageResponse(page);
    }
}
