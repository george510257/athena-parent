package com.gls.athena.starter.amap.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 兴趣点
 */
@Data
public class Poi implements Serializable {
    /**
     * poi 的 id
     */
    private String id;
    /**
     * poi 点名称
     */
    private String name;
    /**
     * poi 类型
     */
    private String type;
    /**
     * 电话
     */
    private String tel;
    /**
     * 该 POI 的中心点到请求坐标的距离
     */
    private String distance;
    /**
     * 方向
     */
    private String direction;
    /**
     * poi 地址信息
     */
    private String address;
    /**
     * 坐标点
     */
    private String location;
    /**
     * poi 所在商圈名称
     */
    private String businessArea;
}
