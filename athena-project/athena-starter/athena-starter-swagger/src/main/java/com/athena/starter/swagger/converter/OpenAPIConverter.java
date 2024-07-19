package com.athena.starter.swagger.converter;

import com.athena.common.core.base.IConverter;
import com.athena.starter.swagger.model.OpenApi;
import io.swagger.v3.oas.models.OpenAPI;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * OpenApi转换器
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {SecurityRequirementConverter.class, PathsConverter.class})
public interface OpenAPIConverter extends IConverter<OpenApi, OpenAPI> {
}
