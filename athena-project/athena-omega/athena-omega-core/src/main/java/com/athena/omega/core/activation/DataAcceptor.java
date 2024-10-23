package com.athena.omega.core.activation;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据接收器
 *
 * @author george
 */
@Data
public class DataAcceptor {

    /**
     * 请求参数
     */
    private final Map<String, RequestParameter<?>> parameters = new HashMap<>();

    /**
     * 获取请求参数
     *
     * @param name 参数名称
     * @param type 参数类型
     * @param <T>  参数类型
     * @return 请求参数
     */
    public <T> RequestParameter<T> getParameter(String name, Class<T> type) {
        if (!parameters.containsKey(name)) {
            parameters.put(name, new RequestParameter<T>().setType(type));
        }
        return (RequestParameter<T>) parameters.get(name);
    }

    /**
     * 获取请求参数
     *
     * @param name 参数名称
     * @return 请求参数
     */
    public RequestParameter<?> getParameter(String name) {
        if (!parameters.containsKey(name)) {
            parameters.put(name, new RequestParameter<>());
        }
        return parameters.get(name);
    }
}
