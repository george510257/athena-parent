package com.gls.athena.omega.mvp.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 提示框
 *
 * @author george
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Alert extends BaseComponent {
    /**
     * 类型
     */
    private String type;
    /**
     * 消息
     */
    private String message;
    /**
     * 是否对话框
     */
    private boolean isDialog = true;
}
