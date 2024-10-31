package com.athena.omega.core.action;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Action普通处理器描述
 *
 * @param <Request>
 * @param <Response>
 * @author george
 */
@Data
@Accessors(chain = true)
public class ActionNormalProcessorDescriptor<Request, Response> implements Serializable {
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
     * 处理器
     */
    private Consumer<ActionRunner<Request, Response>> processor;
}
