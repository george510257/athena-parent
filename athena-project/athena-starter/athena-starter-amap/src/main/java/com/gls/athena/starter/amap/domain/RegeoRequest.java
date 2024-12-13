package com.gls.athena.starter.amap.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 逆地理编码请求
 *
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RegeoRequest extends BaseRequest {
    /**
     * 经纬度坐标
     */
    private String location;
    /**
     * 返回附近 POI 类型
     */
    private String poitype;
    /**
     * 搜索半径
     */
    private String radius;
    /**
     * 返回结果控制
     */
    private String extensions;
    /**
     * 道路等级
     */
    private String roadlevel;
    /**
     * 是否优化 POI 返回顺序
     */
    private String homeorcorp;
}
