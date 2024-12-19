package com.gls.athena.starter.amap.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 基础响应
 *
 * @author george
 */
@Data
public abstract class BaseResponse implements Serializable {
    private String status;
    private String info;
    @JsonProperty("infocode")
    private String infoCode;
}
