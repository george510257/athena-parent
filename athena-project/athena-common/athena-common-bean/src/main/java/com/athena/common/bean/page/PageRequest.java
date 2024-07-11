package com.athena.common.bean.page;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 分页查询VO
 *
 * @param <T>
 */
@Data
@Accessors(chain = true)
public class PageRequest<T> implements Serializable {
    /**
     * 页码
     */
    private Integer page = 1;
    /**
     * 每页条数
     */
    private Integer size = 10;
    /**
     * 排序字段
     */
    private String sort;
    /**
     * 排序方式
     */
    private String order;
    /**
     * 查询参数
     */
    private T params;
}
