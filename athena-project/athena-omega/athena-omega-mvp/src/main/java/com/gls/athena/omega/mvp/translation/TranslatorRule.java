package com.gls.athena.omega.mvp.translation;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 翻译规则
 *
 * @author george
 */
@Data
@Accessors(chain = true)
public class TranslatorRule implements Serializable {
    /**
     * 代码
     */
    private String code;
    /**
     * 语言
     */
    private String lang;
    /**
     * 值
     */
    private String value;

}
