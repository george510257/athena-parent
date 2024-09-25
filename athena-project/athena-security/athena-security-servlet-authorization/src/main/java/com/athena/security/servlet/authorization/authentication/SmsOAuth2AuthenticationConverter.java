package com.athena.security.servlet.authorization.authentication;

import cn.hutool.core.util.StrUtil;
import com.athena.security.servlet.authorization.config.AuthorizationConstants;
import com.athena.security.servlet.authorization.util.AuthenticationUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * OAuth2 短信认证转换器
 *
 * @author george
 */
public class SmsOAuth2AuthenticationConverter extends BaseOAuth2AuthenticationConverter {

    /**
     * 转换
     *
     * @param parameterMap    参数
     * @param clientPrincipal 客户端主体
     * @param scopes          范围
     * @return 认证信息
     */
    @Override
    protected Authentication convert(MultiValueMap<String, String> parameterMap, Authentication clientPrincipal, Set<String> scopes) {
        // 手机号 (REQUIRED)
        String mobile = parameterMap.getFirst(AuthorizationConstants.MOBILE);
        if (StrUtil.isBlank(mobile) || parameterMap.get(AuthorizationConstants.MOBILE).size() != 1) {
            AuthenticationUtil.throwError(OAuth2ErrorCodes.INVALID_REQUEST, AuthorizationConstants.MOBILE, AuthorizationConstants.ERROR_URI);
        }
        // 额外参数
        Map<String, Object> additionalParameters = parameterMap.entrySet().stream()
                .filter(entry -> !List.of(OAuth2ParameterNames.GRANT_TYPE, AuthorizationConstants.MOBILE, OAuth2ParameterNames.SCOPE)
                        .contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().size() > 1 ? entry.getValue() : entry.getValue().getFirst()));
        // 返回 SmsOAuth2AuthenticationToken 对象
        return new SmsOAuth2AuthenticationToken(clientPrincipal, additionalParameters, scopes, mobile);
    }

    /**
     * 是否支持此convert
     *
     * @param grantType 授权类型
     * @return 是否支持
     */
    @Override
    public boolean support(String grantType) {
        return AuthorizationConstants.SMS.getValue().equals(grantType);
    }
}
