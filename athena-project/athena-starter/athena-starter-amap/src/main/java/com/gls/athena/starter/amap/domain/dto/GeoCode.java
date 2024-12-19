package com.gls.athena.starter.amap.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 地理编码
 *
 * @author george
 */
@Data
public class GeoCode implements Serializable {

    /**
     * 格式化地址
     * 例如：北京市朝阳区阜通东大街6号
     */
    @JsonProperty("formatted_address")
    private String formattedAddress;
    /**
     * 国家
     * 国内地址默认返回中国
     */
    private String country;
    /**
     * 地址所在的省份名
     * 例如：北京市。此处需要注意的是，中国的四大直辖市也算作省级单位。
     */
    private String province;
    /**
     * 地址所在的城市名
     * 例如：北京市
     */
    private String city;
    /**
     * 城市编码
     * 例如：010
     */
    @JsonProperty("citycode")
    private String cityCode;
    /**
     * 地址所在的区
     * 例如：朝阳区
     */
    private String district;
    /**
     * 街道
     * 例如：阜通东大街
     */
    private String street;
    /**
     * 门牌
     * 例如：6号
     */
    private String number;
    /**
     * 区域编码
     * 例如：110101
     */
    @JsonProperty("adcode")
    private String adCode;
    /**
     * 坐标点
     * 经度，纬度
     */
    private String location;
    /**
     * 匹配级别
     * 参见下方的地理编码匹配级别列表
     */
    private String level;
}
