package com.gls.athena.starter.dynamic.datasource.support;

import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.provider.AbstractJdbcDataSourceProvider;
import com.gls.athena.common.core.constant.BaseConstants;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * 从数据源中获取 配置信息
 *
 * @author george
 */
public class DefaultJdbcDataSourceProvider extends AbstractJdbcDataSourceProvider {
    /**
     * 数据源配置
     */
    private final DataSourceProperties dataSourceProperties;
    /**
     * 动态数据源配置
     */
    private final DynamicDataSourceProperties dynamicDataSourceProperties;

    /**
     * 构造函数
     *
     * @param defaultDataSourceCreator    默认数据源创建器
     * @param dataSourceProperties        数据源配置
     * @param dynamicDataSourceProperties 动态数据源配置
     */
    public DefaultJdbcDataSourceProvider(DefaultDataSourceCreator defaultDataSourceCreator, DataSourceProperties dataSourceProperties, DynamicDataSourceProperties dynamicDataSourceProperties) {
        super(defaultDataSourceCreator, dataSourceProperties.getDriverClassName(), dataSourceProperties.getUrl(), dataSourceProperties.getUsername(), dataSourceProperties.getPassword());
        this.dataSourceProperties = dataSourceProperties;
        this.dynamicDataSourceProperties = dynamicDataSourceProperties;
    }

    /**
     * 执行语句获得数据源参数
     *
     * @param statement 语句
     * @return 数据源参数
     * @throws SQLException sql异常
     */
    @Override
    protected Map<String, DataSourceProperty> executeStmt(Statement statement) throws SQLException {
        // 查询数据源
        ResultSet rs = statement.executeQuery(dynamicDataSourceProperties.getQueryDsSql());

        // 转换为数据源配置
        Map<String, DataSourceProperty> dataSourcePropertiesMap = toDataSourcePropertiesMap(rs);

        // 添加默认主数据源
        DataSourceProperty property = new DataSourceProperty();
        property.setDriverClassName(dataSourceProperties.getDriverClassName());
        property.setUrl(dataSourceProperties.getUrl());
        property.setUsername(dataSourceProperties.getUsername());
        property.setPassword(dataSourceProperties.getPassword());
        property.setLazy(true);
        dataSourcePropertiesMap.put(BaseConstants.DEFAULT_DATASOURCE_NAME, property);
        return dataSourcePropertiesMap;
    }

    /**
     * 转换为数据源配置
     *
     * @param rs 结果集
     * @return 数据源配置
     */
    private Map<String, DataSourceProperty> toDataSourcePropertiesMap(ResultSet rs) {

        Map<String, DataSourceProperty> dataSourcePropertiesMap = new HashMap<>(16);
        try {
            while (rs.next()) {
                DataSourceProperty property = new DataSourceProperty();
                property.setDriverClassName(rs.getString(dynamicDataSourceProperties.getDsDriverColumn()));
                property.setUrl(rs.getString(dynamicDataSourceProperties.getDsUrlColumn()));
                property.setUsername(rs.getString(dynamicDataSourceProperties.getDsUsernameColumn()));
                property.setPassword(rs.getString(dynamicDataSourceProperties.getDsPasswordColumn()));
                property.setLazy(true);
                dataSourcePropertiesMap.put(rs.getString(dynamicDataSourceProperties.getDsNameColumn()), property);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dataSourcePropertiesMap;
    }
}
