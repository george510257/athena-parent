package com.athena.security.authorization.authentication;

import cn.hutool.core.util.StrUtil;
import com.athena.common.core.util.WebUtil;
import com.athena.security.authorization.util.AuthUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.MultiValueMap;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * OAuth2 密码认证转换器
 */
public class OAuth2PasswordAuthenticationConverter implements AuthenticationConverter {
    /**
     * 错误 URI
     */
    private static final String ERROR_URI = "https://tools.ietf.org/html/rfc6749#section-4.3.2";

    /**
     * 将请求中的参数转换为 OAuth2PasswordAuthenticationToken
     *
     * @param request 请求
     * @return OAuth2PasswordAuthenticationToken 对象
     */
    @Override
    public Authentication convert(HttpServletRequest request) {
        // 获取请求中的参数
        MultiValueMap<String, String> parameterMap = WebUtil.getParameterMap(request);
        // 获取授权类型
        String grantType = parameterMap.getFirst(OAuth2ParameterNames.GRANT_TYPE);
        if (!AuthorizationGrantType.PASSWORD.getValue().equals(grantType)) {
            return null;
        }
        // 获取客户端主体
        Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();
        // 获取请求的范围
        String scope = parameterMap.getFirst(OAuth2ParameterNames.SCOPE);
        if (StrUtil.isNotBlank(scope) && parameterMap.get(OAuth2ParameterNames.SCOPE).size() != 1) {
            AuthUtil.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.SCOPE, ERROR_URI);
        }
        // 请求的范围
        Set<String> scopes = new HashSet<>(StrUtil.split(scope, ' '));
        // 用户名 (REQUIRED)
        String username = parameterMap.getFirst(OAuth2ParameterNames.USERNAME);
        if (StrUtil.isBlank(username) || parameterMap.get(OAuth2ParameterNames.USERNAME).size() != 1) {
            AuthUtil.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.USERNAME, ERROR_URI);
        }
        // 密码 (REQUIRED)
        String password = parameterMap.getFirst(OAuth2ParameterNames.PASSWORD);
        if (StrUtil.isBlank(password) || parameterMap.get(OAuth2ParameterNames.PASSWORD).size() != 1) {
            AuthUtil.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.PASSWORD, ERROR_URI);
        }
        // 额外参数
        Map<String, Object> additionalParameters = WebUtil.getParameterMap(request).entrySet().stream()
                .filter(entry -> !List.of(OAuth2ParameterNames.GRANT_TYPE, OAuth2ParameterNames.USERNAME, OAuth2ParameterNames.PASSWORD, OAuth2ParameterNames.SCOPE)
                        .contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().size() > 1 ? entry.getValue() : entry.getValue().getFirst()));
        // 返回 OAuth2PasswordAuthenticationToken 对象
        return new OAuth2PasswordAuthenticationToken(username, password, scopes, clientPrincipal, additionalParameters);
    }
}
