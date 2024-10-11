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
     * 雪花算法名称
     *
     * @return 雪花算法名称
     */
    String name() default "snowflake";

    /**
     * 工作ID
     *
     * @return 工作ID
     */
    long workerId() default 0L;

    /**
     * 数据中心ID
     *
     * @return 数据中心ID
     */
    long datacenterId() default 0L;
}
