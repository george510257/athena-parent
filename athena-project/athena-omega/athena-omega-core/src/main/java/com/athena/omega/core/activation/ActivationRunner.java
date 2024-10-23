package com.athena.omega.core.activation;

import com.athena.omega.core.request.RequestRecord;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 激活运行器
 *
 * @param <Request>  请求
 * @param <Response> 响应
 * @author george
 */
@Data
@Accessors(chain = true)
public class ActivationRunner<Request, Response> {

    private RequestRecord requestRecord;

    private ActivationRunnerProcess runnerProcess;

    public void validate(DataAcceptor dataAcceptor, Request request) {

    }

    public Response execute(OperationDescriber<Request, Response> operationDescriber, Request request) {
        return null;
    }
}
