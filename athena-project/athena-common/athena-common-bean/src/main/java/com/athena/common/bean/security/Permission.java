package com.athena.common.bean.security;

import com.athena.common.bean.base.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "权限信息", description = "权限信息")
public class Permission extends BaseVo implements IPermission {

    @Schema(title = "权限名", description = "权限名")
    private String name;
    @Schema(title = "权限编码", description = "权限编码")
    private String code;
    @Schema(title = "权限描述", description = "权限描述")
    private String description;
    @Schema(title = "权限类型", description = "权限类型")
    private String type;
    @Schema(title = "父权限ID", description = "父权限ID")
    private Long parentId;
    @Schema(title = "排序", description = "排序")
    private Integer sort;
    @Schema(title = "资源指令", description = "资源指令")
    private String command;
}
