package com.athena.common.bean.jackson2;

import com.athena.common.bean.security.Organization;
import com.athena.common.bean.security.Role;
import com.athena.common.bean.security.User;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonDeserialize(using = UserMixin.UserDeserializer.class)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class UserMixin {

    public static class UserDeserializer extends JsonDeserializer<User> {
        @Override
        public User deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            // 获取ObjectMapper
            ObjectMapper mapper = (ObjectMapper) parser.getCodec();
            // 获取JsonNode
            JsonNode node = mapper.readTree(parser);

            // 获取JsonNode中的字段值
            String username = node.get("username").asText();

            String password = node.get("password").asText();

            String mobile = node.get("mobile").asText();

            String email = node.get("email").asText();

            String realName = node.get("realName").asText();

            String nickName = node.get("nickName").asText();

            String avatar = node.get("avatar").asText();

            String language = node.get("language").asText();

            String locale = node.get("locale").asText();

            String timeZone = node.get("timeZone").asText();

            Role role = mapper.convertValue(node.get("role"), Role.class);

            Organization organization = mapper.convertValue(node.get("organization"), Organization.class);

            List<Role> roles = mapper.convertValue(node.get("roles"), new TypeReference<>() {
            });

            List<Organization> organizations = mapper.convertValue(node.get("organizations"), new TypeReference<>() {
            });

            // 构建User对象
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setMobile(mobile);
            user.setEmail(email);
            user.setRealName(realName);
            user.setNickName(nickName);
            user.setAvatar(avatar);
            user.setLanguage(language);
            user.setLocale(locale);
            user.setTimeZone(timeZone);
            user.setRole(role);
            user.setOrganization(organization);
            user.setRoles(roles);
            user.setOrganizations(organizations);
            JacksonUtil.deserializeBaseVo(mapper, node, user);
            return user;
        }
    }
}
