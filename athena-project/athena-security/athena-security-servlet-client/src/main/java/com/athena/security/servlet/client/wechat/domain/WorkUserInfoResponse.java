package com.athena.security.servlet.client.wechat.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 企业微信用户信息响应
 *
 * @author george
 */
@Data
public class WorkUserInfoResponse implements Serializable {
    /**
     * 返回码
     */
    private Integer errcode;
    /**
     * 对返回码的文本描述内容
     */
    private String errmsg;
    /**
     * 成员UserID。对应管理端的账号，企业内必须唯一。不区分大小写，长度为1~64个字节；第三方应用返回的值为open_userid
     */
    private String userid;
    /**
     * 成员名称；第三方不可获取，调用时返回userid以代替name；代开发自建应用需要管理员授权才返回；对于非第三方创建的成员，第三方通讯录应用也不可获取；未返回name的情况需要通过通讯录展示组件来展示名字
     */
    private String name;
    /**
     * 手机号码，代开发自建应用需要管理员授权且成员oauth2授权获取；第三方仅通讯录应用可获取；对于非第三方创建的成员，第三方通讯录应用也不可获取；上游企业不可获取下游企业成员该字段
     */
    private String mobile;
    /**
     * 成员所属部门id列表，仅返回该应用有查看权限的部门id；成员授权模式下，固定返回根部门id，即固定为1。对授权了“组织架构信息”权限的第三方应用或授权了“组织架构信息”-“部门及父部门ID、部门负责人”权限的代开发应用，返回成员所属的全部部门id
     */
    private String department;
    /**
     * 部门内的排序值，默认为0。数量必须和department一致，数值越大排序越前面。值范围是[0, 2^32)。成员授权模式下不返回该字段
     */
    private String order;
    /**
     * 职务信息；代开发自建应用需要管理员授权才返回；第三方仅通讯录应用可获取；对于非第三方创建的成员，第三方通讯录应用也不可获取；上游企业不可获取下游企业成员该字段
     */
    private String position;
    /**
     * 性别。0表示未定义，1表示男性，2表示女性。代开发自建应用需要管理员授权且成员oauth2授权获取；第三方仅通讯录应用可获取；对于非第三方创建的成员，第三方通讯录应用也不可获取；上游企业不可获取下游企业成员该字段。注：不可获取指返回值0
     */
    private String gender;
    /**
     * 邮箱，代开发自建应用需要管理员授权且成员oauth2授权获取；第三方仅通讯录应用可获取；对于非第三方创建的成员，第三方通讯录应用也不可获取；上游企业不可获取下游企业成员该字段
     */
    private String email;
    /**
     * 企业邮箱，代开发自建应用需要管理员授权且成员oauth2授权获取；第三方仅通讯录应用可获取；对于非第三方创建的成员，第三方通讯录应用也不可获取；上游企业不可获取下游企业成员该字段
     */
    @JsonProperty("biz_mail")
    private String bizMail;
    /**
     * 表示在所在的部门内是否为部门负责人，数量与department一致；第三方通讯录应用或者授权了“组织架构信息-应用可获取企业的部门组织架构信息-部门负责人”权限的第三方应用和代开发应用可获取；对于非第三方创建的成员，第三方通讯录应用不可获取；上游企业不可获取下游企业成员该字段
     */
    @JsonProperty("is_leader_in_dept")
    private String isLeaderInDept;
    /**
     * 直属上级UserID，返回在应用可见范围内的直属上级列表，最多有1个直属上级；第三方通讯录应用或者授权了“组织架构信息-应用可获取可见范围内成员组织架构信息-直属上级”权限的第三方应用和代开发应用可获取；对于非第三方创建的成员，第三方通讯录应用不可获取；上游企业不可获取下游企业成员该字段
     */
    @JsonProperty("direct_leader")
    private String directLeader;
    /**
     * 头像url。 代开发自建应用需要管理员授权且成员oauth2授权获取；第三方仅通讯录应用可获取；对于非第三方创建的成员，第三方通讯录应用也不可获取；上游企业不可获取下游企业成员该字段
     */
    private String avatar;
    /**
     * 头像缩略图url。第三方仅通讯录应用可获取；对于非第三方创建的成员，第三方通讯录应用也不可获取；上游企业不可获取下游企业成员该字段
     */
    @JsonProperty("thumb_avatar")
    private String thumbAvatar;
    /**
     * 座机。代开发自建应用需要管理员授权才返回；第三方仅通讯录应用可获取；对于非第三方创建的成员，第三方通讯录应用也不可获取；上游企业不可获取下游企业成员该字段
     */
    private String telephone;
    /**
     * 别名；第三方仅通讯录应用可获取；对于非第三方创建的成员，第三方通讯录应用也不可获取；上游企业不可获取下游企业成员该字段
     */
    private String alias;
    /**
     * 扩展属性，字段详见成员扩展属性。代开发自建应用需要管理员授权才返回；第三方仅通讯录应用可获取；对于非第三方创建的成员，第三方通讯录应用也不可获取；上游企业不可获取下游企业成员该字段
     */
    private String extattr;
    /**
     * 激活状态: 1=已激活，2=已禁用，4=未激活，5=退出企业。
     * 已激活代表已激活企业微信或已关注微信插件（原企业号）。未激活代表既未激活企业微信又未关注微信插件（原企业号）。
     */
    private String status;
    /**
     * 员工个人二维码，扫描可添加为外部联系人(注意返回的是一个url，可在浏览器上打开该url以展示二维码)；代开发自建应用需要管理员授权且成员oauth2授权获取；第三方仅通讯录应用可获取；对于非第三方创建的成员，第三方通讯录应用也不可获取；上游企业不可获取下游企业成员该字段
     */
    @JsonProperty("qr_code")
    private String qrCode;
    /**
     * 成员对外属性，字段详情见对外属性；代开发自建应用需要管理员授权才返回；第三方仅通讯录应用可获取；对于非第三方创建的成员，第三方通讯录应用也不可获取；上游企业不可获取下游企业成员该字段
     */
    @JsonProperty("external_profile")
    private String externalProfile;
    /**
     * 对外职务，如果设置了该值，则以此作为对外展示的职务，否则以position来展示。代开发自建应用需要管理员授权才返回；第三方仅通讯录应用可获取；对于非第三方创建的成员，第三方通讯录应用也不可获取；上游企业不可获取下游企业成员该字段
     */
    @JsonProperty("external_position")
    private String externalPosition;
    /**
     * 地址。代开发自建应用需要管理员授权且成员oauth2授权获取；第三方仅通讯录应用可获取；对于非第三方创建的成员，第三方通讯录应用也不可获取；上游企业不可获取下游企业成员该字段
     */
    private String address;
    /**
     * 全局唯一。对于同一个服务商，不同应用获取到企业内同一个成员的open_userid是相同的，最多64个字节。仅第三方应用可获取。
     */
    @JsonProperty("open_userid")
    private String openUserid;
    /**
     * 主部门，仅当应用对主部门有查看权限时返回。
     */
    @JsonProperty("main_department")
    private String mainDepartment;
}
