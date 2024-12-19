package com.gls.athena.starter.amap.domain;

import com.gls.athena.starter.amap.domain.dto.GeoCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 地理编码响应
 *
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GeoResponse extends BaseResponse {

    private String count;

    private List<GeoCode> geocodes = new ArrayList<>();
}
