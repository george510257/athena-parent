package com.gls.athena.starter.dynamic.datasource.support;

import com.gls.athena.common.core.constant.BaseConstants;
import com.gls.athena.common.core.constant.BaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 动态数据源配置
 *
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = BaseConstants.BASE_PROPERTIES_PREFIX + ".dynamic.datasource")
public class DynamicDataSourceProperties extends BaseProperties {
    /**
     * 数据源名称列
     */
    private String dsNameColumn = "name";
    /**
     * 用户名列
     */
    private String dsUsernameColumn = "username";
    /**
     * 密码列
     */
    private String dsPasswordColumn = "password";
    /**
     * 数据源url列
     */
    private String dsUrlColumn = "url";
    /**
     * 数据源驱动类列
     */
    private String dsDriverColumn = "driver_class_name";
    /**
     * 查询数据源的SQL
     */
    private String queryDsSql = "select * from gen_datasource_conf where del_flag = 0";
}
