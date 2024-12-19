package com.gls.athena.starter.amap.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 门牌信息列表
 *
 * @author george
 */
@Data
public class StreetNumber implements Serializable {
    private String number;
    private String location;
    private String direction;
    private String distance;
    private String street;
}
