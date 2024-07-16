package com.athena.starter.swagger.model;

import io.swagger.v3.oas.models.PathItem;
import lombok.Data;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.io.Serializable;
import java.util.Map;

@Data
public class OpenApiPaths implements Serializable {
    @NestedConfigurationProperty
    private Map<String, PathItem> paths = null;
    @NestedConfigurationProperty
    private Map<String, Object> extensions = null;
}
