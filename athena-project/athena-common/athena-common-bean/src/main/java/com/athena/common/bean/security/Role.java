package com.athena.common.bean.security;

import com.athena.common.bean.base.BaseVo;
import com.athena.common.bean.jackson2.RoleDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 角色信息
 *
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "角色信息", description = "角色信息")
@JsonDeserialize(using = RoleDeserializer.class)
public class Role extends BaseVo implements IRole<Permission> {

    /**
     * 角色名
     */
    @Schema(title = "角色名", description = "角色名")
    private String name;
    /**
     * 角色编码
     */
    @Schema(title = "角色编码", description = "角色编码")
    private String code;
    /**
     * 角色描述
     */
    @Schema(title = "角色描述", description = "角色描述")
    private String description;
    /**
     * 角色类型
     */
    @Schema(title = "角色类型", description = "角色类型")
    private String type;
    /**
     * 父角色ID
     */
    @Schema(title = "父角色ID", description = "父角色ID")
    private Long parentId;
    /**
     * 排序
     */
    @Schema(title = "排序", description = "排序")
    private Integer sort;
    /**
     * 权限列表
     */
    @Schema(title = "权限列表", description = "权限列表")
    private List<Permission> permissions;

}
