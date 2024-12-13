package com.gls.athena.starter.amap.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 逆地理编码响应
 *
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RegeoResponse extends BaseResponse {
    /**
     * 逆地理编码列表
     */
    private RegeoCode regeocode;

    /**
     * 逆地理编码
     *
     * @author george
     */
    @Data
    public static class RegeoCode implements Serializable {
        /**
         * 地址元素列表
         */
        private AddressComponent addressComponent;
    }

    /**
     * 地址元素
     *
     * @author george
     */
    @Data
    public static class AddressComponent implements Serializable {
        /**
         * 坐标点所在国家名称
         */
        private String country;
        /**
         * 坐标点所在省名称
         */
        private String province;
        /**
         * 坐标点所在城市名称
         */
        private String city;
        /**
         * 城市编码
         */
        private String citycode;
        /**
         * 坐标点所在区名称
         */
        private String district;
        /**
         * 行政区编码
         */
        private String adcode;
        /**
         * 坐标点所在乡镇/街道（此街道为社区街道，不是道路信息）
         */
        private String township;
        /**
         * 乡镇街道编码
         */
        private String towncode;
        /**
         * 社区信息列表
         */
        private Neighborhood neighborhood;
        /**
         * 楼信息列表
         */
        private Building building;
        /**
         * 门牌信息列表
         */
        private StreetNumber streetNumber;
        /**
         * 所属海域信息
         */
        private String seaArea;
        /**
         * 经纬度所属商圈列表
         */
        private BusinessArea businessAreas;
        /**
         * 道路信息列表
         */
        private List<Road> road;
    }

    /**
     * 社区信息列表
     *
     * @author george
     */
    @Data
    public static class Neighborhood implements Serializable {
        /**
         * 社区名称
         */
        private String name;
        /**
         * POI 类型
         */
        private String type;
    }

    /**
     * 门牌信息列表
     *
     * @author george
     */
    @Data
    public static class StreetNumber implements Serializable {
        /**
         * 街道名称
         */
        private String street;
        /**
         * 门牌号
         */
        private String number;
        /**
         * 坐标点
         */
        private String location;
        /**
         * 方向
         */
        private String direction;
        /**
         * 门牌地址到请求坐标的距离
         */
        private String distance;
    }

    /**
     * 楼信息列表
     *
     * @author george
     */
    @Data
    public static class Building implements Serializable {
        /**
         * 建筑名称
         */
        private String name;
        /**
         * 类型
         */
        private String type;
    }

    /**
     * 商圈信息
     *
     * @author george
     */
    @Data
    public static class BusinessArea implements Serializable {
        /**
         * 商圈中心点经纬度
         */
        private String location;
        /**
         * 商圈名称
         */
        private String name;
        /**
         * 商圈所在区域的 adcode
         */
        private String id;
    }

    /**
     * 道路信息
     */
    @Data
    public static class Road implements Serializable {
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
}
