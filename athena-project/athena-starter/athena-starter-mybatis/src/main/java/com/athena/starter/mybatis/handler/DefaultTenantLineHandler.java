package com.athena.starter.mybatis.handler;

import com.athena.common.bean.security.IUserHelper;
import com.athena.common.core.constant.BaseConstants;
import com.athena.starter.mybatis.config.MybatisProperties;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import jakarta.annotation.Resource;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.stereotype.Component;

@Component
public class DefaultTenantLineHandler implements TenantLineHandler {
    @Resource
    private MybatisProperties mybatisProperties;
    @Resource
    private IUserHelper userHelper;

    @Override
    public Expression getTenantId() {
        Long tenantId = userHelper.getCurrentUserTenantId().orElse(BaseConstants.DEFAULT_TENANT_ID);
        return new LongValue(tenantId);
    }

    @Override
    public boolean ignoreTable(String tableName) {
        return mybatisProperties.getIgnoreTenantTable().contains(tableName);
    }
}
