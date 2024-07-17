package com.athena.common.bean.security;

import com.athena.common.bean.base.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 组织信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "组织信息", description = "组织信息")
public class Organization extends BaseVo implements IOrganization {

    /**
     * 组织名
     */
    @Schema(title = "组织名", description = "组织名")
    private String name;
    /**
     * 组织编码
     */
    @Schema(title = "组织编码", description = "组织编码")
    private String code;
    /**
     * 组织描述
     */
    @Schema(title = "组织描述", description = "组织描述")
    private String description;
    /**
     * 组织类型
     */
    @Schema(title = "组织类型", description = "组织类型")
    private String type;
    /**
     * 父组织ID
     */
    @Schema(title = "父组织ID", description = "父组织ID")
    private Long parentId;
    /**
     * 排序
     */
    @Schema(title = "排序", description = "排序")
    private Integer sort;
}
