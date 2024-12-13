package com.gls.athena.starter.amap.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 地理编码响应
 *
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GeoResponse extends BaseResponse {
    /**
     * 返回结果总数目
     */
    private Long count;
    /**
     * 地理编码信息列表
     */
    private List<GeoCode> geocodes;

    /**
     * 地理编码
     *
     * @author george
     */
    @Data
    public static class GeoCode implements Serializable {
        /**
         * 国家
         */
        private String country;
        /**
         * 地址所在的省份名
         */
        private String province;
        /**
         * 地址所在的城市名
         */
        private String city;
        /**
         * 城市编码
         */
        private String citycode;
        /**
         * 地址所在的区
         */
        private String district;
        /**
         * 街道
         */
        private String street;
        /**
         * 门牌
         */
        private String number;
        /**
         * 区域编码
         */
        private String adcode;
        /**
         * 坐标点
         */
        private String location;
        /**
         * 匹配级别
         */
        private String level;
    }
}
