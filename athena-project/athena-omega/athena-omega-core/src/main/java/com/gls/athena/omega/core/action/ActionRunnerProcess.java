package com.gls.athena.omega.core.action;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Action运行过程
 *
 * @author george
 */
@Data
@Accessors(chain = true)
public class ActionRunnerProcess implements Serializable {

    /**
     * 参数
     */
    private final Map<String, Object> params = new HashMap<>();
    /**
     * 标签
     */
    private String type;
    /**
     * 标识
     */
    private String flag;
    /**
     * 描述
     */
    private String description;
    /**
     * 时间
     */
    private Date time;

    /**
     * 添加参数
     *
     * @param key   键
     * @param value 值
     * @return 过程
     */
    public ActionRunnerProcess addParam(String key, Object value) {
        params.put(key, value);
        return this;
    }
}
