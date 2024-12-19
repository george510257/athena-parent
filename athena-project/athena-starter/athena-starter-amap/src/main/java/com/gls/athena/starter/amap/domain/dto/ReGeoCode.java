package com.gls.athena.starter.amap.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 逆地理编码
 *
 * @author george
 */
@Data
public class ReGeoCode implements Serializable {
    /**
     * 地址元素列表
     */
    private AddressComponent addressComponent;
    /**
     * 道路信息列表
     */
    private List<Road> road;
    /**
     * 道路交叉口列表
     */
    private List<Roadinter> roadinters;
    /**
     * 兴趣点信息列表
     */
    private List<Poi> pois;
    /**
     * Aoi信息列表
     */
    private List<Aoi> aois;
}
