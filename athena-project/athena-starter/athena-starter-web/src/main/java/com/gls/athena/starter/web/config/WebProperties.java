package com.gls.athena.starter.web.config;

import com.gls.athena.common.core.constant.BaseConstants;
import com.gls.athena.common.core.constant.BaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * web配置类
 *
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = BaseConstants.BASE_PROPERTIES_PREFIX + ".web")
public class WebProperties extends BaseProperties {
    /**
     * 结果忽略
     */
    private ResultIgnore resultIgnore = new ResultIgnore();

    /**
     * 结果忽略
     */
    @Data
    public static class ResultIgnore implements Serializable {
        /**
         * 返回类型
         */
        List<String> returnType = new ArrayList<>();
        /**
         * 转换器类型
         */
        List<String> converterType = new ArrayList<>();
    }
}
