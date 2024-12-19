package com.gls.athena.starter.amap.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gls.athena.starter.amap.domain.dto.GeoCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 地理编码响应
 *
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GeoResponse extends BaseResponse {
    /**
     * 返回结果总数目
     */
    private Long count;
    /**
     * 地理编码信息列表
     */
    @JsonProperty("geocodes")
    private List<GeoCode> geoCodes;

}
