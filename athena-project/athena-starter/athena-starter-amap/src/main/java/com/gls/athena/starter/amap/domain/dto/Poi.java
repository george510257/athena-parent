package com.gls.athena.starter.amap.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 兴趣点
 */
@Data
public class Poi implements Serializable {
    private String id;
    private String direction;
    private BusinessArea businessarea;
    private String address;
    private String poiweight;
    private String name;
    private String location;
    private String distance;
    private String tel;
    private String type;
}
