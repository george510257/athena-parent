package com.gls.athena.common.bean.base;

import com.alibaba.excel.annotation.ExcelProperty;
import com.gls.athena.common.bean.excel.DeletedConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 基础实体
 *
 * @author george
 */
@Data
@Schema(title = "基础实体", description = "基础实体")
public abstract class BaseVo implements IDomain {
    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    @Schema(title = "主键", description = "主键")
    private Long id;
    /**
     * 租户ID
     */
    @ExcelProperty(value = "租户ID")
    @Schema(title = "租户ID", description = "租户ID")
    private Long tenantId;
    /**
     * 版本号
     */
    @ExcelProperty(value = "版本号")
    @Schema(title = "版本号", description = "版本号")
    private Integer version;
    /**
     * 删除标记 0:正常;1:已删除
     */
    @ExcelProperty(value = "删除标记", converter = DeletedConverter.class)
    @Schema(title = "删除标记 0:正常;1:已删除", description = "删除标记 0:正常;1:已删除")
    private Boolean deleted;
    /**
     * 创建人ID
     */
    @ExcelProperty(value = "创建人ID")
    @Schema(title = "创建人ID", description = "创建人ID")
    private Long createUserId;
    /**
     * 创建人姓名
     */
    @ExcelProperty(value = "创建人姓名")
    @Schema(title = "创建人姓名", description = "创建人姓名")
    private String createUserName;
    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    @Schema(title = "创建时间", description = "创建时间")
    private Date createTime;
    /**
     * 更新人ID
     */
    @ExcelProperty(value = "更新人ID")
    @Schema(title = "更新人ID", description = "更新人ID")
    private Long updateUserId;
    /**
     * 更新人姓名
     */
    @ExcelProperty(value = "更新人姓名")
    @Schema(title = "更新人姓名", description = "更新人姓名")
    private String updateUserName;
    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    @Schema(title = "更新时间", description = "更新时间")
    private Date updateTime;
}
