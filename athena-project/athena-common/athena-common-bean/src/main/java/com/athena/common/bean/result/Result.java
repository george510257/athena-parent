package com.athena.common.bean.result;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 返回结果VO
 *
 * @param <T> 数据类型
 * @author george
 */
@Data
@Accessors(chain = true)
public class Result<T> implements Serializable {

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 消息
     */
    private String message;
    /**
     * 数据
     */
    private T data;

}
