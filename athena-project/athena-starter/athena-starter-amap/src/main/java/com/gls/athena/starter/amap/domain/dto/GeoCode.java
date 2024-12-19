package com.gls.athena.starter.amap.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 地理编码
 *
 * @author george
 */
@Data
public class GeoCode implements Serializable {
    private String formatted_address;
    private String country;
    private String province;
    private String citycode;
    private String city;
    private String district;
    private String township;
    private Neighborhood neighborhood;
    private Building building;
    private String adcode;
    private String street;
    private String number;
    private String location;
    private String level;
}
