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
public class ReGeoResponse extends BaseResponse {
    /**
     * 逆地理编码列表
     */
    private ReGeoCode reGeoCode;

    /**
     * 逆地理编码
     *
     * @author george
     */
    @Data
    public static class ReGeoCode implements Serializable {
        /**
         * 地址元素列表
         */
        private AddressComponent addressComponent;
        /**
         * 道路信息列表
         */
        private List<Road> road;
        /**
         * 道路交叉口列表
         */
        private List<Roadinter> roadinters;
        /**
         * 兴趣点信息列表
         */
        private List<Poi> pois;
        /**
         * Aoi信息列表
         */
        private List<Aoi> aois;
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
         * 例如：中国
         */
        private String country;
        /**
         * 坐标点所在省名称
         * 例如：北京市
         */
        private String province;
        /**
         * 坐标点所在城市名称
         * 请注意：当城市是省直辖县时返回为空，以及城市为北京、上海、天津、重庆四个直辖市时，该字段返回为空；省直辖县列表
         */
        private String city;
        /**
         * 城市编码
         * 例如：010
         */
        private String cityCode;
        /**
         * 坐标点所在区名称
         * 例如：海淀区
         */
        private String district;
        /**
         * 行政区编码
         * 例如：110108
         */
        private String adCode;
        /**
         * 坐标点所在乡镇/街道（此街道为社区街道，不是道路信息）
         * 例如：燕园街道
         */
        private String township;
        /**
         * 乡镇街道编码
         * 例如：110101001000
         */
        private String townCode;
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

    /**
     * 道路交叉口
     */
    @Data
    public static class Roadinter implements Serializable {
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

    /**
     * 兴趣点
     */
    @Data
    public static class Poi implements Serializable {
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

    /**
     * Aoi
     */
    @Data
    public static class Aoi implements Serializable {
        /**
         * 所属 aoi 的 id
         */
        private String id;
        /**
         * 所属 aoi 名称
         */
        private String name;
        /**
         * 所属 aoi 所在区域编码
         */
        private String adCode;
        /**
         * 所属 aoi 中心点坐标
         */
        private String location;
        /**
         * 所属 aoi 点面积
         */
        private String area;
        /**
         * 输入经纬度是否在 aoi 面之中
         */
        private String distance;
        /**
         * 所属 aoi 类型
         */
        private String type;
    }
}
