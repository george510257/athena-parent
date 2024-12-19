package com.gls.athena.starter.amap.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 地址元素
 *
 * @author george
 */
@Data
public class AddressComponent implements Serializable {
    /**
     * 坐标点所在城市名称
     */
    private String city;
    /**
     * 坐标点所在省名称
     */
    private String province;
    /**
     * 行政区编码
     */
    private String adcode;
    /**
     * 坐标点所在区
     */
    private String district;
    /**
     * 乡镇街道编码
     */
    private String towncode;
    /**
     * 门牌信息
     */
    private StreetNumber streetNumber;
    /**
     * 坐标点所在国家名称
     */
    private String country;
    /**
     * 坐标点所在乡镇/街道（此街道为社区街道，不是道路信息）
     */
    private String township;
    /**
     * 商圈信息
     */
    private List<BusinessArea> businessAreas;
    /**
     * 楼信息
     */
    private Building building;
    /**
     * 社区信息
     */
    private Neighborhood neighborhood;
    /**
     * 城市编码
     */
    private String citycode;

}
