package com.gls.athena.starter.amap.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 楼信息列表
 *
 * @author george
 */
@Data
public class Building implements Serializable {
    private String name;
    private String type;
}
