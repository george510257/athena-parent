package com.athena.security.servlet.client.social;

import com.athena.common.bean.jackson2.JacksonUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.IOException;

/**
 * 社交用户反序列化
 *
 * @author george
 */
public class SocialUserDeserializer extends JsonDeserializer<SocialUser> {
    @Override
    public SocialUser deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        // 获取ObjectMapper
        ObjectMapper mapper = (ObjectMapper) parser.getCodec();
        // 获取JsonNode
        JsonNode node = mapper.readTree(parser);
        // 获取JsonNode中的字段值
        OAuth2User oauth2User = mapper.convertValue(node.get("oauth2User"), OAuth2User.class);
        String providerId = node.get("providerId").asText();
        String username = node.get("username").asText();
        Boolean bindStatus = node.get("bindStatus").asBoolean();
        SocialUser socialUser = new SocialUser();
        socialUser.setOauth2User(oauth2User);
        socialUser.setProviderId(providerId);
        socialUser.setUsername(username);
        socialUser.setBindStatus(bindStatus);
        JacksonUtil.deserializeBaseVo(mapper, node, socialUser);
        return socialUser;
    }
}
