package com.athena.starter.dynamic.datasource.support;

import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;

/**
 * 动态数据源配置
 *
 * @author george
 */
@AutoConfiguration
public class DynamicDataSourceConfig {

    /**
     * 默认数据源提供者
     *
     * @param defaultDataSourceCreator    默认数据源创建器
     * @param dataSourceProperties        数据源配置
     * @param dynamicDataSourceProperties 动态数据源配置
     * @return 数据源提供者
     */
    @Bean
    @ConditionalOnProperty(prefix = "athena.dynamic.datasource", name = "enabled", havingValue = "true", matchIfMissing = true)
    public DefaultJdbcDataSourceProvider defaultJdbcDataSourceProvider(DefaultDataSourceCreator defaultDataSourceCreator,
                                                                       DataSourceProperties dataSourceProperties,
                                                                       DynamicDataSourceProperties dynamicDataSourceProperties) {
        return new DefaultJdbcDataSourceProvider(defaultDataSourceCreator, dataSourceProperties, dynamicDataSourceProperties);
    }
}
