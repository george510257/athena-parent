package com.athena.starter.swagger.model;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import lombok.Data;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class OpenApi implements Serializable {
    private String openapi = "3.0.1";
    @NestedConfigurationProperty
    private Info info = null;
    @NestedConfigurationProperty
    private ExternalDocumentation externalDocs = null;
    @NestedConfigurationProperty
    private List<Server> servers = null;
    @NestedConfigurationProperty
    private List<SecurityRequirement> security = null;
    @NestedConfigurationProperty
    private List<Tag> tags = null;
    @NestedConfigurationProperty
    private OpenApiPaths paths = null;
    @NestedConfigurationProperty
    private Components components = null;
    private Map<String, Object> extensions = null;
    private String jsonSchemaDialect;
    private SpecVersion specVersion = SpecVersion.V30;
    @NestedConfigurationProperty
    private Map<String, PathItem> webhooks = null;
}
