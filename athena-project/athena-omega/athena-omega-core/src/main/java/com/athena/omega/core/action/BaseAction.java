package com.athena.omega.core.action;

import cn.hutool.core.util.StrUtil;
import com.athena.omega.core.request.RequestRecord;
import lombok.Data;

import java.io.Serializable;
import java.util.function.Function;

/**
 * 基础动作
 *
 * @author george
 */
@Data
public abstract class BaseAction<Request, Response> implements Function<Request, Response>, Serializable {
    /**
     * 名称
     */
    private final String name;
    /**
     * 分组
     */
    private final String group;
    /**
     * 描述
     */
    private final String description;
    /**
     * 动作数据接收器
     */
    private final ActionDataReceiver actionDataReceiver = new ActionDataReceiver();
    /**
     * 动作处理描述器
     */
    private final ActionProcessorDescriptor<Request, Response> actionProcessorDescriptor = new ActionProcessorDescriptor<>();
    /**
     * 请求记录
     */
    private final RequestRecord requestRecord;

    /**
     * 构建数据接收器
     *
     * @param actionDataReceiver 动作数据接收器
     */
    protected abstract void buildDataReceiver(ActionDataReceiver actionDataReceiver);

    /**
     * 构建处理描述器
     *
     * @param actionProcessorDescriptor 动作处理描述器
     */
    protected abstract void buildProcessorDescriptor(ActionProcessorDescriptor<Request, Response> actionProcessorDescriptor);

    /**
     * 执行
     *
     * @param request 请求
     * @return 响应
     */
    @Override
    public Response apply(Request request) {
        // 初始化动作执行器
        ActionRunner<Request, Response> runner = new ActionRunner<>(requestRecord);
        // 设置运行过程
        runner.setProcess(new ActionRunnerProcess()
                .setType("accept")
                .setFlag(StrUtil.toUnderlineCase(this.getClass().getSimpleName()))
                .setDescription("写入请求参数."));
        // 构建数据接收器
        this.buildDataReceiver(actionDataReceiver);
        // 测试数据接收器
        if (runner.test(actionDataReceiver, request)) {
            throw new IllegalArgumentException("数据接收器测试失败.");
        }
        // 构建处理描述器
        this.buildProcessorDescriptor(actionProcessorDescriptor);
        // 执行处理器
        return runner.apply(actionProcessorDescriptor);
    }
}
