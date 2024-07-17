package com.athena.common.core.base;

/**
 * 定制器
 *
 * @param <T> 定制对象
 */
@FunctionalInterface
public interface ICustomizer<T> {
    /**
     * 默认定制器
     *
     * @param <T> 定制对象
     * @return 默认定制器
     */
    static <T> ICustomizer<T> withDefaults() {
        return (t) -> {
        };
    }

    /**
     * 定制
     *
     * @param t 定制对象
     */
    void customize(T t);
}
