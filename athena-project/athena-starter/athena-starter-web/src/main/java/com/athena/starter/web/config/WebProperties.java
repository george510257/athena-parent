package com.athena.starter.web.config;

import com.athena.common.core.constant.BaseConstants;
import com.athena.common.core.constant.BaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = BaseConstants.BASE_PROPERTIES_PREFIX + ".web")
public class WebProperties extends BaseProperties {

    private ResultIgnore resultIgnore = new ResultIgnore();

    @Data
    public static class ResultIgnore implements Serializable {

        List<String> returnType = new ArrayList<>();

        List<String> converterType = new ArrayList<>();
    }
}
