package com.athena.starter.data.jpa.support;

import org.hibernate.annotations.IdGeneratorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 雪花算法注解
 *
 * @author george
 */
@IdGeneratorType(SnowflakeIdGenerator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SnowflakeId {

    /**
     * 工作id
     *
     * @return 工作id
     */
    long workerId() default 0;

    /**
     * 数据中心id
     *
     * @return 数据中心id
     */
    long datacenterId() default 0;
}
