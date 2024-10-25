package com.athena.security.servlet.client.wechat.work;

import com.athena.security.servlet.client.delegate.IAuthorizationCodeTokenResponseClientAdapter;
import com.athena.security.servlet.client.wechat.WechatProperties;
import jakarta.annotation.Resource;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Component;

/**
 * 企业微信授权码令牌响应客户端定制器
 *
 * @author george
 */
@Component
public class WorkAuthorizationCodeTokenResponseClientAdapter implements IAuthorizationCodeTokenResponseClientAdapter {
    /**
     * 企业微信属性配置
     */
    @Resource
    private WechatProperties wechatProperties;

    /**
     * 测试是否支持指定的注册标识
     *
     * @param registrationId 注册标识
     * @return 是否支持
     */
    @Override
    public boolean test(String registrationId) {
        return wechatProperties.getWork().getRegistrationId().equals(registrationId);
    }

//    /**
//     * 定制化
//     *
//     * @param client 授权码令牌响应客户端
//     */
//    @Override
//    public void customize(DefaultAuthorizationCodeTokenResponseClient client) {
//        client.setRequestEntityConverter(this::requestEntityConverter);
//    }
//
//    /**
//     * 请求实体转换器
//     *
//     * @param request  授权码授权请求
//     * @param response 响应
//     * @return 请求实体
//     */
//    @Override
//    public OAuth2AccessTokenResponse customResponse(OAuth2AuthorizationCodeGrantRequest request, OAuth2AccessTokenResponse response) {
//        return OAuth2AccessTokenResponse.withResponse(response)
//                .additionalParameters(MapUtil.builder(new HashMap<String, Object>(1))
//                        // 添加授权码
//                        .put(OAuth2ParameterNames.CODE, request.getAuthorizationExchange()
//                                .getAuthorizationResponse().getCode())
//                        .build())
//                .build();
//    }
//
//    /**
//     * 请求实体转换器
//     *
//     * @param request 授权码授权请求
//     * @return 请求实体
//     */
//    private RequestEntity<?> requestEntityConverter(OAuth2AuthorizationCodeGrantRequest request) {
//        // 请求参数
//        MultiValueMap<String, String> parameters = this.convertParameters(request);
//        // 请求地址
//        URI uri = UriComponentsBuilder.fromUriString(request.getClientRegistration().getProviderDetails().getTokenUri())
//                .queryParams(parameters).build().toUri();
//        // 创建请求实体
//        return RequestEntity.get(uri).build();
//    }
//
//    /**
//     * 转换请求参数
//     *
//     * @param request 授权码授权请求
//     * @return 请求参数
//     */
//    private MultiValueMap<String, String> convertParameters(OAuth2AuthorizationCodeGrantRequest request) {
//        MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<>();
//        ClientRegistration clientRegistration = request.getClientRegistration();
//        queryParameters.add(ClientSecurityConstants.WECHAT_WORK_CORP_ID, clientRegistration.getClientId());
//        queryParameters.add(ClientSecurityConstants.WECHAT_WORK_CORP_SECRET, clientRegistration.getClientSecret());
//        return queryParameters;
//    }

    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
        return null;
    }
}
