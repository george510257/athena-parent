package com.athena.starter.data.jpa.support;

import org.hibernate.annotations.IdGeneratorType;
import org.hibernate.annotations.ValueGenerationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 雪花算法注解
 *
 * @author george
 */
@ValueGenerationType(generatedBy = SnowflakeGenerator.class)
@IdGeneratorType(SnowflakeGenerator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Snowflake {
}
