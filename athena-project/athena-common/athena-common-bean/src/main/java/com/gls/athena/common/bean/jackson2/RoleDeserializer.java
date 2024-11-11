package com.gls.athena.common.bean.jackson2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gls.athena.common.bean.security.Permission;
import com.gls.athena.common.bean.security.Role;

import java.io.IOException;
import java.util.List;

/**
 * 角色反序列化
 *
 * @author george
 */
public class RoleDeserializer extends JsonDeserializer<Role> {
    /**
     * 将JSON内容反序列化为Role对象
     *
     * @param parser  JsonParser JSON解析器
     * @param context DeserializationContext 上下文
     * @return Role 角色对象
     * @throws IOException IO异常
     */
    @Override
    public Role deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        // 获取ObjectMapper
        ObjectMapper mapper = (ObjectMapper) parser.getCodec();
        // 获取JsonNode
        JsonNode node = mapper.readTree(parser);

        String name = node.get("name").asText();

        String code = node.get("code").asText();

        String description = node.get("description").asText();

        String type = node.get("type").asText();

        Long parentId = node.get("parentId").asLong();

        Integer sort = node.get("sort").asInt();

        List<Permission> permissions = mapper.convertValue(node.get("permissions"), new TypeReference<>() {
        });

        // 创建Role对象
        Role role = new Role();
        role.setName(name);
        role.setCode(code);
        role.setDescription(description);
        role.setType(type);
        role.setParentId(parentId);
        role.setSort(sort);
        role.setPermissions(permissions);
        JacksonUtil.deserializeBaseVo(mapper, node, role);
        return role;
    }
}
