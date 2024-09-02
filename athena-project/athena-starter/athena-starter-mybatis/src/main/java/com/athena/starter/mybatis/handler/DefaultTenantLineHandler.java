package com.athena.starter.mybatis.handler;

import com.athena.common.bean.security.IUserHelper;
import com.athena.common.core.constant.BaseConstants;
import com.athena.starter.mybatis.config.MybatisProperties;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import jakarta.annotation.Resource;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.stereotype.Component;

/**
 * 默认租户处理器
 *
 * @author george
 */
@Component
public class DefaultTenantLineHandler implements TenantLineHandler {
    /**
     * Mybatis配置
     */
    @Resource
    private MybatisProperties mybatisProperties;
    /**
     * 用户帮助类
     */
    @Resource
    private IUserHelper userHelper;

    /**
     * 获取租户ID
     *
     * @return 租户ID
     */
    @Override
    public Expression getTenantId() {
        Long tenantId = userHelper.getCurrentUserTenantId().orElse(BaseConstants.DEFAULT_TENANT_ID);
        return new LongValue(tenantId);
    }

    /**
     * 获取租户字段
     *
     * @param tableName 表名
     * @return 租户字段
     */
    @Override
    public boolean ignoreTable(String tableName) {
        return mybatisProperties.getIgnoreTenantTable().contains(tableName);
    }
}
