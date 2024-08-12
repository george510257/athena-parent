package com.athena.common.bean.jackson2;

import com.athena.common.bean.base.BaseVo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.util.Date;

@UtilityClass
public class JacksonUtil {

    public void deserializeBaseVo(ObjectMapper mapper, JsonNode node, BaseVo baseVo) {
        // 获取JsonNode中的字段值
        Long id = node.get("id").asLong();

        Long tenantId = node.get("tenantId").asLong();

        Integer version = node.get("version").asInt();

        Boolean deleted = node.get("deleted").asBoolean();

        Long createUserId = node.get("createUserId").asLong();

        String createUserName = node.get("createUserName").asText();

        Date createTime = mapper.convertValue(node.get("createTime"), Date.class);

        Long updateUserId = node.get("updateUserId").asLong();

        String updateUserName = node.get("updateUserName").asText();

        Date updateTime = mapper.convertValue(node.get("updateTime"), Date.class);

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
