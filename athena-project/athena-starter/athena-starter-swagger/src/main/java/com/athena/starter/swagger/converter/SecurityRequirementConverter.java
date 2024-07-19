package com.athena.starter.swagger.converter;

import com.athena.common.core.base.IConverter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.Map;

/**
 * OpenApiSecurityRequirement转换器
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SecurityRequirementConverter extends IConverter<Map<String, List<String>>, SecurityRequirement> {
}
