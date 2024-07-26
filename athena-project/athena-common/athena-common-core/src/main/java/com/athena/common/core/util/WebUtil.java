package com.athena.common.core.util;

import cn.hutool.core.collection.CollUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@UtilityClass
public class WebUtil {

    public MultiValueMap<String, String> getParameterMap(HttpServletRequest request) {
        return request.getParameterMap()
                .entrySet()
                .stream()
                .collect(LinkedMultiValueMap::new, (map, entry) -> map.put(entry.getKey(), CollUtil.newArrayList(entry.getValue())), LinkedMultiValueMap::putAll);

    }
}
