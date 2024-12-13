package com.gls.athena.starter.amap.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 地理编码请求
 *
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GeoRequest extends BaseRequest {
    /**
     * 结构化地址信息
     */
    private String address;
    /**
     * 指定查询的城市
     */
    private String city;
}
