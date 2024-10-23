package com.athena.omega.core.activation;

import cn.hutool.core.util.StrUtil;
import com.athena.omega.core.request.RequestRecord;

/**
 * 激活器
 *
 * @param <Request>  请求
 * @param <Response> 响应
 * @author george
 */
public interface Activation<Request, Response> {
    /**
     * 获取分组
     *
     * @return 分组
     */
    String getGroup();

    /**
     * 获取名称
     *
     * @return 名称
     */
    String getName();

    /**
     * 获取描述
     *
     * @return 描述
     */
    String getDescription();

    /**
     * 构建数据接收器
     *
     * @param dataAcceptor 数据接收器
     */
    void buildDataAcceptor(DataAcceptor dataAcceptor);

    /**
     * 构建操作描述器
     *
     * @param operationDescriber 操作描述器
     */
    void buildOperationDescriber(OperationDescriber<Request, Response> operationDescriber);

    /**
     * 获取请求记录
     *
     * @return 请求记录
     */
    RequestRecord getRequestRecord();

    /**
     * 获取范例记录
     *
     * @return 范例记录
     */
    default String getSampleRecord() {
        return null;
    }

    /**
     * 处理请求
     *
     * @param request 请求
     * @return 响应
     * @throws Exception 异常
     */
    default Response process(Request request) throws Exception {
        // 初始化执行器
        RequestRecord requestRecord = this.getRequestRecord();
        ActivationRunner<Request, Response> runner = new ActivationRunner<>();
        runner.setRequestRecord(requestRecord);

        // 写入请求参数
        runner.setRunnerProcess(new ActivationRunnerProcess()
                .setType("accept")
                .setFlag(StrUtil.toUnderlineCase(this.getClass().getSimpleName()))
                .setDescription("写入请求参数"));

        // 参数合法校验
        DataAcceptor dataAcceptor = new DataAcceptor();
        this.buildDataAcceptor(dataAcceptor);
        runner.validate(dataAcceptor, request);

        // 执行业务流程
        OperationDescriber<Request, Response> operationDescriber = new OperationDescriber<>();
        this.buildOperationDescriber(operationDescriber);
        return runner.execute(operationDescriber, request);
    }
}
