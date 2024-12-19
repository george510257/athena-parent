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
    private List<Road> roads;
    private List<Roadinter> roadinters;
    private String formatted_address;
    private AddressComponent addressComponent;
    private List<Aoi> aois;
    private List<Poi> pois;
}
