package com.gls.athena.starter.amap.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 地址元素
 *
 * @author george
 */
@Data
public class AddressComponent implements Serializable {
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
