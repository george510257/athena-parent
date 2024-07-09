package com.athena.common.bean.page;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询响应VO
 *
 * @param <T>
 */
@Data
@Accessors(chain = true)
public class PageResponse<T> implements Serializable {

    /**
     * 页码
     */
    private Integer page;
    /**
     * 每页条数
     */
    private Integer size;
    /**
     * 总条数
     */
    private Long total;
    /**
     * 总页数
     */
    private Integer pages;
    /**
     * 数据
     */
    private List<T> data;
}
