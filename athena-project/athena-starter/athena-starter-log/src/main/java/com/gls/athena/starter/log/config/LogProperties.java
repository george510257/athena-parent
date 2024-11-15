package com.gls.athena.starter.log.config;

import com.gls.athena.common.core.constant.BaseConstants;
import com.gls.athena.common.core.constant.BaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 日志配置
 *
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = BaseConstants.BASE_PROPERTIES_PREFIX + ".log")
public class LogProperties extends BaseProperties {
    /**
     * kafka topic
     */
    private String kafkaTopic = "athena-log";
    /**
     * 是否开启kafka
     */
    private boolean kafkaEnable = false;
}
