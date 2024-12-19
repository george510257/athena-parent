package com.gls.athena.starter.amap.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 道路信息
 */
@Data
public class Road implements Serializable {
    private String id;
    private String location;
    private String direction;
    private String name;
    private String distance;
}
