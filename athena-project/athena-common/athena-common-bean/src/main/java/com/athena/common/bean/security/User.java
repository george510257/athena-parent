package com.athena.common.bean.security;

import com.athena.common.bean.base.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "用户信息", description = "用户信息")
public class User extends BaseVo implements IUser<Role, Permission, Organization> {

    @Schema(title = "用户名", description = "用户名")
    private String username;
    @Schema(title = "密码", description = "密码")
    private String password;
    @Schema(title = "手机号", description = "手机号")
    private String mobile;
    @Schema(title = "邮箱", description = "邮箱")
    private String email;
    @Schema(title = "姓名", description = "姓名")
    private String realName;
    @Schema(title = "昵称", description = "昵称")
    private String nickName;
    @Schema(title = "头像", description = "头像")
    private String avatar;
    @Schema(title = "语言", description = "语言")
    private String language;
    @Schema(title = "国家", description = "国家")
    private String locale;
    @Schema(title = "时区", description = "时区")
    private String timeZone;

    @Schema(title = "当前角色", description = "当前角色")
    private Role role;
    @Schema(title = "当前组织", description = "当前组织")
    private Organization organization;

    @Schema(title = "角色列表", description = "角色列表")
    private List<Role> roles;
    @Schema(title = "组织列表", description = "组织列表")
    private List<Organization> organizations;

}
