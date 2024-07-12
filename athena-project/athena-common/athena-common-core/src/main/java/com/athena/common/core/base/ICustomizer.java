package com.athena.common.core.base;

@FunctionalInterface
public interface ICustomizer<T> {

    static <T> ICustomizer<T> withDefaults() {
        return (t) -> {
        };
    }

    void customize(T t);
}
