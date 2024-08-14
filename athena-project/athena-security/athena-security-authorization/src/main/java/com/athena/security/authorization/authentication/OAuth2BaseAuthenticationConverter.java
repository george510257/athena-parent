package com.athena.security.authorization.authentication;

import cn.hutool.core.util.StrUtil;
import com.athena.security.authorization.config.AuthorizationConstants;
import com.athena.security.authorization.util.AuthenticationUtil;
import com.athena.starter.web.util.WebUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.MultiValueMap;

import java.util.HashSet;
import java.util.Set;

/**
 * OAuth2 基础认证转换器
 */
public abstract class OAuth2BaseAuthenticationConverter implements AuthenticationConverter {
    /**
     * 转换
     *
     * @param request 请求
     * @return 认证信息
     */
    @Override
    public Authentication convert(HttpServletRequest request) {
        // 获取请求中的参数
        MultiValueMap<String, String> parameterMap = WebUtil.getParameterMap(request);
        // 获取授权类型
        String grantType = parameterMap.getFirst(OAuth2ParameterNames.GRANT_TYPE);
        if (!support(grantType)) {
            return null;
        }

        // 获取客户端主体
        Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();
        if (clientPrincipal == null) {
            AuthenticationUtil.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ErrorCodes.INVALID_CLIENT, AuthorizationConstants.ERROR_URI);
        }
        // 获取请求的范围
        String scope = parameterMap.getFirst(OAuth2ParameterNames.SCOPE);
        if (StrUtil.isNotBlank(scope) && parameterMap.get(OAuth2ParameterNames.SCOPE).size() != 1) {
            AuthenticationUtil.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.SCOPE, AuthorizationConstants.ERROR_URI);
        }
        // 请求的范围
        Set<String> scopes = new HashSet<>(StrUtil.split(scope, ' '));
        return convert(parameterMap, clientPrincipal, scopes);
    }

    /**
     * 转换
     *
     * @param parameterMap    参数
     * @param clientPrincipal 客户端主体
     * @param scopes          范围
     * @return 认证信息
     */
    protected abstract Authentication convert(MultiValueMap<String, String> parameterMap, Authentication clientPrincipal, Set<String> scopes);

    /**
     * 是否支持
     *
     * @param grantType 授权类型
     * @return 是否支持
     */
    public abstract boolean support(String grantType);
}
