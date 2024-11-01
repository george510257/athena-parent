package com.athena.omega.mvp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * 大小
 *
 * @author george
 */
@Getter
@RequiredArgsConstructor
public enum Size implements Serializable {
    /**
     * 大
     */
    LARGE(0, "large"),
    /**
     * 最大
     */
    MAXIMUM(1, "maximum"),
    /**
     * 正常
     */
    NORMAL(2, "normal"),
    /**
     * 小
     */
    SMALL(3, "small"),
    /**
     * 微小
     */
    TINY(4, "tiny"),
    /**
     * 最小
     */
    MINIMUM(5, "minimum");
    /**
     * 代码
     */
    private final Integer code;
    /**
     * 值
     */
    private final String value;
}
