package com.gls.athena.common.bean.result;

/**
 * 返回结果状态
 *
 * @author george
 */
public interface IResultStatus {

    /**
     * 获取编码
     *
     * @return 返回编码
     */
    Integer getCode();

    /**
     * 获取消息
     *
     * @return 返回消息
     */
    String getMessage();

    /**
     * 转换为返回结果
     *
     * @return 返回结果
     */
    default <T> Result<T> toResult() {
        return new Result<T>()
                .setCode(getCode())
                .setMessage(getMessage());
    }

    /**
     * 转换为返回结果
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return 返回结果
     */
    default <T> Result<T> toResult(T data) {
        return new Result<T>()
                .setCode(getCode())
                .setMessage(getMessage())
                .setData(data);
    }
}
