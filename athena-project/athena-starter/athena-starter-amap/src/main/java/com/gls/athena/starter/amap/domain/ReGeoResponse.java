package com.gls.athena.starter.amap.domain;

import com.gls.athena.starter.amap.domain.dto.ReGeoCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 逆地理编码响应
 *
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ReGeoResponse extends BaseResponse {
    /**
     * 逆地理编码列表
     */
    private ReGeoCode regeocode;
}
