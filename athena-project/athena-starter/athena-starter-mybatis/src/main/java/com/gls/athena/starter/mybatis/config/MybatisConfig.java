package com.gls.athena.starter.mybatis.config;

import com.baomidou.mybatisplus.autoconfigure.SqlSessionFactoryBeanCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.*;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import java.util.List;

/**
 * Mybatis配置
 *
 * @author george
 */
@AutoConfiguration
public class MybatisConfig {

    /**
     * 乐观锁插件
     */
    @Bean
    @Order(1)
    @ConditionalOnProperty(prefix = "athena.mybatis", name = "optimistic-locker", havingValue = "true", matchIfMissing = true)
    public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }

    /**
     * 租户插件
     */
    @Bean
    @Order(2)
    @ConditionalOnBean(TenantLineHandler.class)
    @ConditionalOnProperty(prefix = "athena.mybatis", name = "tenant", havingValue = "true", matchIfMissing = true)
    public TenantLineInnerInterceptor tenantLineInnerInterceptor(TenantLineHandler tenantLineHandler) {
        return new TenantLineInnerInterceptor(tenantLineHandler);
    }

    /**
     * 数据权限插件
     */
    @Bean
    @Order(3)
    @ConditionalOnBean(DataPermissionHandler.class)
    @ConditionalOnProperty(prefix = "athena.mybatis", name = "data-permission", havingValue = "true", matchIfMissing = true)
    public DataPermissionInterceptor dataPermissionInnerInterceptor(DataPermissionHandler dataPermissionHandler) {
        return new DataPermissionInterceptor(dataPermissionHandler);
    }

    /**
     * 分页插件
     */
    @Bean
    @Order(4)
    @ConditionalOnProperty(prefix = "athena.mybatis", name = "pagination", havingValue = "true", matchIfMissing = true)
    public PaginationInnerInterceptor paginationInnerInterceptor(MybatisProperties mybatisProperties) {
        return new PaginationInnerInterceptor(mybatisProperties.getDbType());
    }

    /**
     * 插件配置
     */
    @Bean
    @ConditionalOnBean(InnerInterceptor.class)
    public MybatisPlusInterceptor mybatisPlusInterceptor(List<InnerInterceptor> innerInterceptors) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        innerInterceptors.forEach(interceptor::addInnerInterceptor);
        return interceptor;
    }

    /**
     * 自定义SqlSessionFactoryBean
     */
    @Bean
    @ConditionalOnBean(TypeHandler.class)
    public SqlSessionFactoryBeanCustomizer sqlSessionFactoryBeanCustomizer(List<TypeHandler<?>> typeHandlers) {
        return sqlSessionFactoryBean -> sqlSessionFactoryBean.setTypeHandlers(typeHandlers.toArray(new TypeHandler<?>[0]));
    }
}
