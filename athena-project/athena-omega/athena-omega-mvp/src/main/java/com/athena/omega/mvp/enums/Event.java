package com.athena.omega.mvp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * 事件
 *
 * @author george
 */
@Getter
@RequiredArgsConstructor
public enum Event implements Serializable {
    /**
     * 提交
     */
    SUBMIT(0, "submit"),
    /**
     * 操作
     */
    ACTION(1, "action"),
    /**
     * 刷新
     */
    REFRESH(2, "refresh");
    /**
     * 代码
     */
    private final Integer code;
    /**
     * 值
     */
    private final String value;
}
