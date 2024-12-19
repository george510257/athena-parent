package com.gls.athena.starter.amap.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 道路交叉口
 *
 * @author george
 */
@Data
public class Roadinter implements Serializable {
    private String second_name;
    private String first_id;
    private String second_id;
    private String location;
    private String distance;
    private String first_name;
    private String direction;
}
