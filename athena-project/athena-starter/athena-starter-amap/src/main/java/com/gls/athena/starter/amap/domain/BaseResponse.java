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
    /**
     * 返回结果状态值
     * 返回值为 0 或 1，0 表示请求失败；1 表示请求成功。
     */
    private Integer status;
    /**
     * 返回状态说明
     * 当 status 为 0 时，info 会返回具体错误原因，否则返回“OK”。详情可以参阅 info 状态表
     */
    private String info;
    /**
     * 返回状态码
     */
    @JsonProperty("infocode")
    private Integer infoCode;
}
