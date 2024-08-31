package com.athena.security.servlet.client.delegate;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

import java.util.Map;
import java.util.function.Predicate;

/**
 * 用户响应转换器
 */
public interface IUserResponseConverter extends Converter<OAuth2UserRequest, Converter<Map<String, Object>, Map<String, Object>>>, Predicate<String> {

    /**
     * 测试是否支持指定的注册标识
     *
     * @param oauth2UserRequest 用户请求
     * @return 是否支持
     */
    @Override
    default Converter<Map<String, Object>, Map<String, Object>> convert(OAuth2UserRequest oauth2UserRequest) {
        return params -> convert(oauth2UserRequest, params);
    }

    /**
     * 转换
     *
     * @param oauth2UserRequest 用户请求
     * @param params            参数
     * @return 转换结果
     */
    Map<String, Object> convert(OAuth2UserRequest oauth2UserRequest, Map<String, Object> params);
}
