package com.athena.starter.data.jpa.support;

import cn.hutool.core.util.IdUtil;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * 雪花算法生成器
 *
 * @author george
 */
public class SnowflakeGenerator implements IdentifierGenerator {
    /**
     * 生成id
     *
     * @param session 会话
     * @param object  对象
     * @return id
     */
    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        return IdUtil.getSnowflakeNextId();
    }
}
