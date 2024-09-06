package com.athena.starter.web.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.JakartaServletUtil;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.experimental.UtilityClass;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import java.util.Optional;

/**
 * web工具类
 *
 * @author george
 */
@UtilityClass
public class WebUtil {

    public MultiValueMap<String, String> getParameterMap(HttpServletRequest request) {
        return request.getParameterMap()
                .entrySet()
                .stream()
                .collect(LinkedMultiValueMap::new, (map, entry) -> map.put(entry.getKey(), CollUtil.newArrayList(entry.getValue())), LinkedMultiValueMap::putAll);

    }

    public Optional<HttpServletRequest> getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return Optional.of(((ServletRequestAttributes) requestAttributes).getRequest());
        }
        return Optional.empty();
    }

    public Optional<HttpServletResponse> getResponse() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return Optional.ofNullable(((ServletRequestAttributes) requestAttributes).getResponse());
        }
        return Optional.empty();
    }

    public String getParameter(HttpServletRequest request, String parameterName) {
        String parameter = WebUtils.findParameterValue(request, parameterName);
        if (StrUtil.isNotBlank(parameter)) {
            return parameter;
        }
        return getParameterByBody(request, parameterName);
    }

    public String getParameterByBody(HttpServletRequest request, String parameterName) {
        String body = JakartaServletUtil.getBody(request);
        if (StrUtil.isNotBlank(body)) {
            return JSONUtil.parseObj(body).getStr(parameterName);
        }
        return null;
    }

    public Optional<HttpSession> getSession() {
        return getRequest().map(HttpServletRequest::getSession);
    }
}
