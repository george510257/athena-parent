package com.athena.omega.mvp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * 方向
 *
 * @author george
 */
@Getter
@RequiredArgsConstructor
public enum Orientation implements Serializable {
    /**
     * 自动
     */
    AUTO(0, "auto"),
    /**
     * 横向
     */
    HORIZONTAL(1, "horizontal"),
    /**
     * 纵向
     */
    VERTICAL(2, "vertical");
    /**
     * 代码
     */
    private final Integer code;
    /**
     * 值
     */
    private final String value;
}
