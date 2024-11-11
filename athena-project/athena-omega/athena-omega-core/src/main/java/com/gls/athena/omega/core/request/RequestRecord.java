package com.gls.athena.omega.core.request;

import com.gls.athena.omega.core.action.ActionRunnerProcess;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求记录
 *
 * @author george
 */
@Data
@Accessors(chain = true)
public class RequestRecord implements Serializable {
    /**
     * 请求头
     */
    private final Map<String, String> headers = new HashMap<>();
    /**
     * 查询参数
     */
    private final Map<String, String> query = new HashMap<>();
    /**
     * 请求参数
     */
    private final Map<String, Object> params = new HashMap<>();
    /**
     * 日志
     */
    private final List<Map<String, Object>> logs = new ArrayList<>();
    /**
     * 埋点
     */
    private final List<Map<String, Object>> points = new ArrayList<>();
    /**
     * 过程
     */
    private final List<ActionRunnerProcess> processes = new ArrayList<>();
    /**
     * 缓冲区
     */
    private final Map<String, Object> buffers = new HashMap<>();
    /**
     * 请求结束回调
     */
    private final List<Runnable> callbacks = new ArrayList<>();
    /**
     * 唯一标识
     */
    private String uuid;
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 事务ID
     */
    private String transactionId;
    /**
     * 语言
     */
    private String language;
    /**
     * 请求时间
     */
    private long requestTime;
    /**
     * 客户端IP
     */
    private String clientIp;
    /**
     * 服务端IP
     */
    private String serverIp;
    /**
     * 方法
     */
    private String method;
    /**
     * 请求路径
     */
    private String path;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 响应结果
     */
    private Object result;
    /**
     * 完结标识
     */
    private boolean finish;
    /**
     * 归档标识
     */
    private boolean archive;
    /**
     * 打印标识
     */
    private boolean print;
    /**
     * 耗时
     */
    private long time;
    /**
     * 日志接收器
     */
    private LogReceiver logReceiver;

    /**
     * 是否有错误日志
     *
     * @return 结果
     */
    public boolean hasErrorLog() {
        return this.logs.stream().anyMatch(log -> "error".equalsIgnoreCase(log.get("type").toString()));
    }

    /**
     * @param process
     * @return
     */
    public RequestRecord addProcess(ActionRunnerProcess process) {
        this.processes.add(process);
        return this;
    }
}
