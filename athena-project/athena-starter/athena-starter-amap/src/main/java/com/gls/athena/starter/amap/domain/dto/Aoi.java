package com.gls.athena.starter.amap.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Aoi
 */
@Data
public class Aoi implements Serializable {
    private String area;
    private String type;
    private String id;
    private String location;
    private String adcode;
    private String name;
    private String distance;
}
