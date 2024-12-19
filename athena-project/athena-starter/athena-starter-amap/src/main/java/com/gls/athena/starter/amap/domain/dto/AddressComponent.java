package com.gls.athena.starter.amap.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 地址元素
 *
 * @author george
 */
@Data
public class AddressComponent implements Serializable {
    private String city;
    private String province;
    private String adcode;
    private String district;
    private String towncode;
    private StreetNumber streetNumber;
    private String country;
    private String township;
    private List<BusinessArea> businessAreas;
    private Building building;
    private Neighborhood neighborhood;
    private String citycode;

}
