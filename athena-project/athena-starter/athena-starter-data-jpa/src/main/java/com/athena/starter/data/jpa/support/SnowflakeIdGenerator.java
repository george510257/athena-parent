package com.athena.starter.data.jpa.support;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.factory.spi.CustomIdGeneratorCreationContext;

import java.lang.reflect.Member;

/**
 * 雪花算法生成器
 *
 * @author george
 */
public class SnowflakeIdGenerator implements IdentifierGenerator {
    /**
     * 雪花算法
     */
    private final Snowflake snowflake;

    /**
     * 构造函数
     *
     * @param snowflakeId 雪花算法
     * @param member      成员
     * @param context     上下文
     */
    public SnowflakeIdGenerator(SnowflakeId snowflakeId, Member member, CustomIdGeneratorCreationContext context) {
        // 创建雪花算法
        this.snowflake = IdUtil.getSnowflake(snowflakeId.workerId(), snowflakeId.datacenterId());
    }

    /**
     * 生成id
     *
     * @param session 会话
     * @param object  对象
     * @return id
     */
    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        return snowflake.nextId();
    }
}
