package com.athena.common.bean.page;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(title = "分页查询VO", description = "分页查询VO")
public class PageRequest<T> implements Serializable {
    /**
     * 页码
     */
    @Schema(title = "页码", description = "页码")
    private Integer page = 1;
    /**
     * 每页条数
     */
    @Schema(title = "每页条数", description = "每页条数")
    private Integer size = 10;
    /**
     * 排序字段
     */
    @Schema(title = "排序字段", description = "排序字段")
    private String sort;
    /**
     * 排序方式
     */
    @Schema(title = "排序方式", description = "排序方式")
    private String order;
    /**
     * 查询参数
     */
    @Schema(title = "查询参数", description = "查询参数")
    private T params;
}
