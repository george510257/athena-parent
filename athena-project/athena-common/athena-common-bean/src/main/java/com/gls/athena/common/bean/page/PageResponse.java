package com.gls.athena.common.bean.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询响应VO
 *
 * @param <T> 数据类型
 * @author george
 */
@Data
@Accessors(chain = true)
@Schema(title = "分页查询响应VO", description = "分页查询响应VO")
public class PageResponse<T> implements Serializable {

    /**
     * 页码
     */
    @Schema(title = "页码", description = "页码")
    private Integer page;
    /**
     * 每页条数
     */
    @Schema(title = "每页条数", description = "每页条数")
    private Integer size;
    /**
     * 总条数
     */
    @Schema(title = "总条数", description = "总条数")
    private Long total;
    /**
     * 总页数
     */
    @Schema(title = "总页数", description = "总页数")
    private Integer pages;
    /**
     * 数据
     */
    @Schema(title = "数据", description = "数据")
    private List<T> data;
}
