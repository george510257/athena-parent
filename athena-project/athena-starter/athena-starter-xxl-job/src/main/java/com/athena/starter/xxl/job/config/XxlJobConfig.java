package com.athena.starter.xxl.job.config;

import cn.hutool.extra.spring.SpringUtil;
import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 定时任务配置
 *
 * @author george
 */
@AutoConfiguration
public class XxlJobConfig {
    /**
     * xxl-job-admin服务ID
     */
    private static final String XXL_JOB_ADMIN_SERVICE_ID = "xxl-job-admin";
    /**
     * xxl-job配置
     */
    @Resource
    private XxlJobProperties xxlJobProperties;
    /**
     * 服务发现客户端
     */
    @Resource
    private Optional<DiscoveryClient> discoveryClient;

    /**
     * xxl-job执行器
     *
     * @return xxl-job执行器
     */
    @Bean
    @ConditionalOnMissingBean
    public XxlJobExecutor xxlJobExecutor() {
        XxlJobExecutor executor = new XxlJobSpringExecutor();
        // 定时任务中心地址
        executor.setAdminAddresses(getAdminAddresses());
        // 执行器AppName
        executor.setAppname(getAppName());
        // 执行器注册地址
        if (xxlJobProperties.getAddress() != null) {
            executor.setAddress(xxlJobProperties.getAddress());
        }
        // 执行器IP
        if (xxlJobProperties.getIp() != null) {
            executor.setIp(xxlJobProperties.getIp());
        }
        // 执行器端口号
        if (xxlJobProperties.getPort() != 0) {
            executor.setPort(xxlJobProperties.getPort());
        }
        // 执行器token
        if (xxlJobProperties.getAccessToken() != null) {
            executor.setAccessToken(xxlJobProperties.getAccessToken());
        }
        // 执行器运行日志文件存储磁盘路径
        if (xxlJobProperties.getLogPath() != null) {
            executor.setLogPath(xxlJobProperties.getLogPath());
        }
        // 执行器日志保存天数
        if (xxlJobProperties.getLogRetentionDays() != 0) {
            executor.setLogRetentionDays(xxlJobProperties.getLogRetentionDays());
        }
        return executor;

    }

    /**
     * 获取应用名称
     *
     * @return 应用名称
     */
    private String getAppName() {
        if (xxlJobProperties.getAppName() != null) {
            return xxlJobProperties.getAppName();
        }
        return SpringUtil.getApplicationName();
    }

    /**
     * 获取调度中心地址
     *
     * @return 调度中心地址
     */
    private String getAdminAddresses() {
        return discoveryClient.map(client -> client.getServices().stream()
                        .filter(serviceId -> serviceId.contains(XXL_JOB_ADMIN_SERVICE_ID))
                        .flatMap(serviceId -> client.getInstances(serviceId).stream())
                        .map(serviceInstance -> serviceInstance.getUri().getAuthority() + "/" + XXL_JOB_ADMIN_SERVICE_ID)
                        .collect(Collectors.joining(",")))
                .orElse(xxlJobProperties.getAdminAddresses());
    }
}
