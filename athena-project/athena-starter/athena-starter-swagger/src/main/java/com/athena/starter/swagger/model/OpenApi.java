package com.athena.starter.swagger.model;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import lombok.Data;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OpenApi 配置
 */
@Data
public class OpenApi implements Serializable {
    /**
     * openapi版本
     */
    private String openapi = "3.0.1";
    /**
     * 信息
     */
    @NestedConfigurationProperty
    private Info info = new Info();
    /**
     * 外部文档
     */
    @NestedConfigurationProperty
    private ExternalDocumentation externalDocs = new ExternalDocumentation();
    /**
     * 服务器
     */
    @NestedConfigurationProperty
    private List<Server> servers = new ArrayList<>();
    /**
     * 安全
     */
    @NestedConfigurationProperty
    private List<Map<String, List<String>>> security = new ArrayList<>();
    /**
     * 标签
     */
    @NestedConfigurationProperty
    private List<Tag> tags = new ArrayList<>();
    /**
     * 路径
     */
    @NestedConfigurationProperty
    private Map<String, PathItem> paths = new HashMap<>();
    /**
     * 组件
     */
    @NestedConfigurationProperty
    private Components components = new Components();
    /**
     * 扩展
     */
    @NestedConfigurationProperty
    private Map<String, Object> extensions = new HashMap<>();
    /**
     * json schema方言
     */
    private String jsonSchemaDialect;
    /**
     * 规范版本
     */
    private SpecVersion specVersion = SpecVersion.V30;
    /**
     * webhooks
     */
    @NestedConfigurationProperty
    private Map<String, PathItem> webhooks = new HashMap<>();
}
