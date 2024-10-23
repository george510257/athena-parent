package com.athena.omega.core.activation;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * 激活运行器进程
 *
 * @author george
 */
@Data
@Accessors(chain = true)
public class ActivationRunnerProcess implements Serializable {
    /**
     * 类型
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
     * 参数
     */
    private Map<String, Object> params;
    /**
     * 时间
     */
    private long time;
}
