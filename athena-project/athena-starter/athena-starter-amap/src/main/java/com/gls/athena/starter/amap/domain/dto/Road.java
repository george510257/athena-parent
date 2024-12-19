package com.gls.athena.starter.amap.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 道路信息
 */
@Data
public class Road implements Serializable {
    /**
     * 道路 id
     */
    private String id;
    /**
     * 道路名称
     */
    private String name;
    /**
     * 道路到请求坐标的距离
     */
    private String distance;
    /**
     * 方位
     */
    private String direction;
    /**
     * 坐标点
     */
    private String location;
}
