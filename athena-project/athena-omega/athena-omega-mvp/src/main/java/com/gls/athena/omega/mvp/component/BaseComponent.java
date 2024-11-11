package com.gls.athena.omega.mvp.component;

import com.gls.athena.omega.mvp.enums.Size;
import com.gls.athena.omega.mvp.translation.TranslatorRule;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 基础组件
 *
 * @author george
 */
@Data
@Accessors(chain = true)
public abstract class BaseComponent implements Serializable {
    /**
     * 事件
     */
    private final Map<String, Consumer<Object>> events = new HashMap<>();
    /**
     * 反馈
     */
    private final Map<String, Function<Object, Object>> feedbacks = new HashMap<>();
    /**
     * 属性
     */
    private final Map<String, Object> properties = new HashMap<>();
    /**
     * 组件ID
     */
    private String id;
    /**
     * 是否显示
     */
    private boolean show;
    /**
     * 聚焦
     */
    private boolean focus;
    /**
     * 独立
     */
    private boolean independent;
    /**
     * 回调
     */
    private Consumer<BaseComponent> callback;
    /**
     * 大小
     */
    private Size size;
    /**
     * 翻译器
     */
    private Function<TranslatorRule, String> translator;
}
