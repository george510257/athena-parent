package com.athena.common.bean.security;

import com.athena.common.bean.base.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "角色信息", description = "角色信息")
public class Role extends BaseVo implements IRole<Permission> {

    @Schema(title = "角色名", description = "角色名")
    private String name;
    @Schema(title = "角色编码", description = "角色编码")
    private String code;
    @Schema(title = "角色描述", description = "角色描述")
    private String description;
    @Schema(title = "角色类型", description = "角色类型")
    private String type;
    @Schema(title = "父角色ID", description = "父角色ID")
    private Long parentId;
    @Schema(title = "排序", description = "排序")
    private Integer sort;
    @Schema(title = "权限列表", description = "权限列表")
    private List<Permission> permissions;

}
