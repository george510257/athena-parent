package com.gls.athena.common.bean.jackson2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gls.athena.common.bean.base.BaseVo;
import lombok.experimental.UtilityClass;

import java.util.Date;

/**
 * Jackson工具类
 *
 * @author george
 */
@UtilityClass
public class JacksonUtil {

    /**
     * 序列化基础实体
     *
     * @param mapper ObjectMapper
     * @param node   JsonNode
     * @param baseVo 基础实体
     */
    public void deserializeBaseVo(ObjectMapper mapper, JsonNode node, BaseVo baseVo) {
        // 获取JsonNode中的字段值
        // 主键
        Long id = node.get("id").asLong();
        // 租户ID
        Long tenantId = node.get("tenantId").asLong();
        // 版本号
        Integer version = node.get("version").asInt();
        // 删除标记
        Boolean deleted = node.get("deleted").asBoolean();
        // 创建人ID
        Long createUserId = node.get("createUserId").asLong();
        // 创建人姓名
        String createUserName = node.get("createUserName").asText();
        // 创建时间
        Date createTime = mapper.convertValue(node.get("createTime"), Date.class);
        // 更新人ID
        Long updateUserId = node.get("updateUserId").asLong();
        // 更新人姓名
        String updateUserName = node.get("updateUserName").asText();
        // 更新时间
        Date updateTime = mapper.convertValue(node.get("updateTime"), Date.class);
        // 设置基础实体字段值
        baseVo.setId(id);
        baseVo.setTenantId(tenantId);
        baseVo.setVersion(version);
        baseVo.setDeleted(deleted);
        baseVo.setCreateUserId(createUserId);
        baseVo.setCreateUserName(createUserName);
        baseVo.setCreateTime(createTime);
        baseVo.setUpdateUserId(updateUserId);
        baseVo.setUpdateUserName(updateUserName);
        baseVo.setUpdateTime(updateTime);
    }
}
