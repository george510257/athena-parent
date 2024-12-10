package com.gls.athena.starter.aliyun.oss.endpoint;

import com.aliyun.oss.OSSClient;
import jakarta.annotation.Resource;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * oss端点
 *
 * @author george
 */
@Endpoint(id = "oss")
public class OssEndpoint {
    /**
     * 应用上下文
     */
    @Resource
    private ApplicationContext applicationContext;

    /**
     * 调用
     *
     * @return 结果
     */
    @ReadOperation
    public Map<String, Object> invoke() {
        Map<String, Object> result = new HashMap();
        applicationContext.getBeansOfType(OSSClient.class)
                .forEach((beanName, client) -> {
                    Map<String, Object> ossProperties = new HashMap();
                    ossProperties.put("beanName", beanName);
                    ossProperties.put("endpoint", client.getEndpoint().toString());
                    ossProperties.put("clientConfiguration", client.getClientConfiguration());
                    ossProperties.put("credentials", client.getCredentialsProvider().getCredentials());
                    ossProperties.put("bucketList", client.listBuckets().stream().map(bucket -> bucket.getName()).toArray());
                    result.put(beanName, ossProperties);
                });
        return result;
    }
}
