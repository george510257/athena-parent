package com.gls.athena.common.bean.jackson2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gls.athena.common.bean.security.Organization;
import com.gls.athena.common.bean.security.Role;
import com.gls.athena.common.bean.security.User;

import java.io.IOException;
import java.util.List;

/**
 * 用户反序列化
 *
 * @author george
 */
public class UserDeserializer extends JsonDeserializer<User> {

    /**
     * 将JSON内容反序列化为User对象
     *
     * @param parser  JSON解析器
     * @param context 上下文
     * @return User 用户对象
     * @throws IOException IO异常
     */
    @Override
    public User deserialize(JsonParser parser, DeserializationContext context) throws IOException {
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
        user.setRoles(roles);
        user.setOrganizations(organizations);
        JacksonUtil.deserializeBaseVo(mapper, node, user);
        return user;
    }
}
