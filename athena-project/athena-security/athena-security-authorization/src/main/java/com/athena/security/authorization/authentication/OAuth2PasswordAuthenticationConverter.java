package com.athena.security.authorization.authentication;

import com.athena.common.core.util.WebUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.MultiValueMap;

public class OAuth2PasswordAuthenticationConverter implements AuthenticationConverter {

    @Override
    public Authentication convert(HttpServletRequest request) {
        // 获取请求中的参数
        MultiValueMap<String, String> parameterMap = WebUtil.getParameterMap(request);
        // 获取授权类型
        String grantType = parameterMap.getFirst(OAuth2ParameterNames.GRANT_TYPE);
        if (!AuthorizationGrantType.PASSWORD.getValue().equals(grantType)) {
            return null;
        }
        // 获取请求的范围
        String scope = parameterMap.getFirst(OAuth2ParameterNames.SCOPE);
        return null;
    }
}
