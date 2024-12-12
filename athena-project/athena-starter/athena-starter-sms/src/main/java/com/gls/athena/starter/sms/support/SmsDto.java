package com.gls.athena.starter.sms.support;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * 短信DTO
 *
 * @author george
 */
@Data
@Accessors(chain = true)
public class SmsDto implements Serializable {
    /**
     * 应用名称
     */
    private String applicationName;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 模板编码
     */
    private String templateCode;
    /**
     * 参数
     */
    private Map<String, Object> params;
}
