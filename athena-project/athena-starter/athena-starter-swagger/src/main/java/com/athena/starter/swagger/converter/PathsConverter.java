package com.athena.starter.swagger.converter;

import com.athena.common.core.base.IConverter;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Map;

/**
 * OpenApiPaths转换器
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PathsConverter extends IConverter<Map<String, PathItem>, Paths> {
}
