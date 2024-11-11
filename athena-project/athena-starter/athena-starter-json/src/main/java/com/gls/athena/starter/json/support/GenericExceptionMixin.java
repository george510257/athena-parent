package com.gls.athena.starter.json.support;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 异常混合类
 *
 * @author george
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonSerialize(using = GenericExceptionSerializer.class)
@JsonDeserialize(using = GenericExceptionDeserializer.class)
public class GenericExceptionMixin {
}
