package com.athena.starter.web.util;

import cn.hutool.core.collection.CollUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.OutputStream;
import java.util.Optional;

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

    public String getParameter(String key) {
        return getRequest()
                .map(WebUtil::getParameterMap)
                .map(map -> map.getFirst(key))
                .orElse(null);
    }

    public OutputStream getOutputStream() {
        return getResponse()
                .map(response -> {
                    try {
                        return response.getOutputStream();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElseThrow(() -> new RuntimeException("获取response输出流失败"));
    }

    public Optional<HttpServletResponse> getResponse() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return Optional.ofNullable(((ServletRequestAttributes) requestAttributes).getResponse());
        }
        return Optional.empty();
    }
}
