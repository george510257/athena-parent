package com.gls.athena.starter.amap.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 基础请求
 *
 * @author george
 */
@Data
public abstract class BaseRequest implements Serializable {
    /**
     * 高德key
     */
    private String key;
    /**
     * 数字签名
     */
    private String sig;
    /**
     * 返回数据格式类型
     */
    private String output;
    /**
     * 回调函数
     */
    private String callback;
}
