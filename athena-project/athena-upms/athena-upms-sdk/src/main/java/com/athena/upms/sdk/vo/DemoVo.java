package com.athena.upms.sdk.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.athena.common.bean.base.BaseVo;
import com.athena.upms.sdk.excel.StatusConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * demo数据 Vo
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "demo", description = "demo数据Vo")
public class DemoVo extends BaseVo {
    /**
     * 名称
     */
    @ExcelProperty(value = "名称")
    @Schema(title = "名称", description = "名称")
    private String name;
    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    @Schema(title = "描述", description = "描述")
    private String description;
    /**
     * 状态 0:正常 1:禁用
     */
    @ExcelProperty(value = "状态 0:正常 1:禁用", converter = StatusConverter.class)
    @Schema(title = "状态 0:正常 1:禁用", description = "状态 0:正常 1:禁用")
    private Boolean status;
}
