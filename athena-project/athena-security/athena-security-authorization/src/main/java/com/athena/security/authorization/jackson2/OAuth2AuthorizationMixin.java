package com.athena.security.authorization.jackson2;

import com.athena.security.authorization.config.AuthorizationConstants;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * oauth2授权混合
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonDeserialize(using = OAuth2AuthorizationMixin.OAuth2AuthorizationDeserializer.class)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class OAuth2AuthorizationMixin {
    /**
     * OAuth2Authorization 反序列化器
     */
    public static class OAuth2AuthorizationDeserializer extends JsonDeserializer<OAuth2Authorization> {

        /**
         * 反序列化 OAuth2Authorization 对象
         *
         * @param parser  JsonParser 对象
         * @param context DeserializationContext 对象
         * @return OAuth2Authorization 对象
         * @throws IOException      IO异常
         * @throws JacksonException Jackson异常
         */
        @Override
        public OAuth2Authorization deserialize(JsonParser parser, DeserializationContext context) throws IOException, JacksonException {
            // 获取ObjectMapper
            ObjectMapper mapper = (ObjectMapper) parser.getCodec();
            // 获取JsonNode
            JsonNode node = mapper.readTree(parser);
            // 获取JsonNode中的字段值
            String id = node.get("id").asText();
            // 注册的客户端ID
            String registeredClientId = node.get("registeredClientId").asText();
            // 主体名称
            String principalName = node.get("principalName").asText();
            // 授权类型
            AuthorizationGrantType authorizationGrantType = mapper.convertValue(node.get("authorizationGrantType"), AuthorizationGrantType.class);
            // 授权范围
            Set<String> authorizedScopes = mapper.convertValue(node.get("authorizedScopes"), new TypeReference<>() {
            });
            // tokens
            Map<String, OAuth2Authorization.Token<?>> tokens = mapper.convertValue(node.get("tokens"), new TypeReference<>() {
            });
            // attributes
            Map<String, Object> attributes = mapper.convertValue(node.get("attributes"), new TypeReference<>() {
            });

            // 构建OAuth2Authorization对象
            OAuth2Authorization.Builder builder = OAuth2Authorization.withRegisteredClient(RegisteredClient.withId(registeredClientId)
                            .clientId("test-client-id")
                            .authorizationGrantType(AuthorizationConstants.PASSWORD)
                            .build())
                    .id(id)
                    .principalName(principalName)
                    .authorizationGrantType(authorizationGrantType)
                    .authorizedScopes(authorizedScopes);
            // 设置tokens
            tokens.forEach((key, value) -> builder.token(value.getToken()));
            // 设置attributes
            attributes.forEach(builder::attribute);
            // 返回OAuth2Authorization对象
            return builder.build();
        }

    }
}
