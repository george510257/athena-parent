package com.athena.starter.swagger.converter;

import com.athena.common.core.base.IConverter;
import com.athena.starter.swagger.model.OpenApiPaths;
import io.swagger.v3.oas.models.Paths;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OpenApiPathsConverter extends IConverter<OpenApiPaths, Paths> {

    @AfterMapping
    default void afterMapping(OpenApiPaths openApiPaths, @MappingTarget Paths paths) {
        if (openApiPaths.getPaths() != null) {
            paths.putAll(openApiPaths.getPaths());
        }
    }

    @AfterMapping
    default void afterMapping(Paths paths, @MappingTarget OpenApiPaths openApiPaths) {
        if (paths != null) {
            openApiPaths.setPaths(paths);
        }
    }
}
