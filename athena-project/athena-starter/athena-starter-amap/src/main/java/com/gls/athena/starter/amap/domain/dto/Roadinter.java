package com.gls.athena.starter.amap.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 道路交叉口
 */
@Data
public class Roadinter implements Serializable {
    /**
     * 交叉路口到请求坐标的距离
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
    /**
     * 第一条道路 id
     */
    private String firstId;
    /**
     * 第一条道路名称
     */
    private String firstName;
    /**
     * 第二条道路 id
     */
    private String secondId;
    /**
     * 第二条道路名称
     */
    private String secondName;
}
