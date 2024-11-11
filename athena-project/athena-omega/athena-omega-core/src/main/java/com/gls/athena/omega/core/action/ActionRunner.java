package com.gls.athena.omega.core.action;

import cn.hutool.core.collection.CollUtil;
import com.gls.athena.omega.core.request.RequestRecord;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * Action运行器
 *
 * @author george
 */
@Data
@Accessors(chain = true)
public class ActionRunner<Request, Response> implements
        BiPredicate<ActionDataReceiver, Request>,
        Function<ActionProcessorDescriptor<Request, Response>, Response> {
    /**
     * 请求记录
     */
    private final RequestRecord requestRecord;
    /**
     * 请求
     */
    private final ActionDataRegistry dataRegistry = new ActionDataRegistry();
    /**
     * 运行过程
     */
    private ActionRunnerProcess process;

    /**
     * 测试
     *
     * @param receiver Action数据接收器
     * @param request  请求
     * @return 是否通过
     */
    @Override
    public boolean test(ActionDataReceiver receiver, Request request) {
        try {
            if (request instanceof Map<?, ?> data) {
                receiver.getParams().forEach((key, actionParam) -> addParam(key, actionParam, data.get(key)));
            }
            requestRecord.addProcess(process);
            return true;
        } catch (Exception e) {
            requestRecord.addProcess(process.addParam("error", e.getMessage()));
            return false;
        }
    }

    /**
     * 添加参数
     *
     * @param key         参数键
     * @param actionParam 参数
     * @param value       参数值
     * @param <T>         参数类型
     */
    private <T> void addParam(String key, ActionParam<T> actionParam, Object value) {
        // 校验必填
        if (actionParam.isRequired() && value == null) {
            throw new IllegalArgumentException("参数" + key + "不能为空");
        }
        // 设置默认值
        if (value == null) {
            dataRegistry.put(key, actionParam.getDefaultValue());
            return;
        }
        // 校验类型
        if (!actionParam.getType().isInstance(value)) {
            throw new IllegalArgumentException("参数" + key + "类型错误");
        }
        T paramValue = actionParam.getType().cast(value);
        // 校验校验器
        if (actionParam.getValidator() != null && !actionParam.getValidator().test(paramValue)) {
            throw new IllegalArgumentException("参数" + key + "校验失败");
        }
        // 转换参数
        if (actionParam.getConverter() != null) {
            dataRegistry.put(key, actionParam.getConverter().apply(paramValue));
        } else {
            dataRegistry.put(key, paramValue);
        }
    }

    /**
     * 执行
     *
     * @param descriptor 动作处理描述器
     * @return 响应
     */
    @Override
    public Response apply(ActionProcessorDescriptor<Request, Response> descriptor) {
        try {
            descriptor.getNormalProcessorDescriptors().forEach(processorDescriptor -> {
                this.process = new ActionRunnerProcess()
                        .setType(processorDescriptor.getType())
                        .setFlag(processorDescriptor.getFlag())
                        .setDescription(processorDescriptor.getDescription());
                processorDescriptor.getProcessor().accept(this);
                this.requestRecord.addProcess(this.process);
            });
            this.process = new ActionRunnerProcess().setType("end");
            Response response = descriptor.getFinalProcessor().apply(this);
            this.requestRecord.addProcess(this.process);
            return response;
        } catch (Exception e) {
            return descriptor.getExceptionProcessorDescriptors().stream()
                    .filter(processor -> processor.test(e.getClass()) || CollUtil.isEmpty(processor.getClasses()))
                    .map(processor -> {
                        this.process = new ActionRunnerProcess().setType("crash")
                                .addParam("class", e.getClass().getName())
                                .addParam("message", e.getMessage())
                                .addParam("process-type", "end");
                        Response response = processor.getFallback().apply(this, e);
                        this.requestRecord.addProcess(this.process);
                        return response;
                    }).findFirst().orElse(null);
        }
    }
}
