package com.gls.athena.starter.data.jpa.support;

import cn.hutool.core.util.IdUtil;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.factory.spi.CustomIdGeneratorCreationContext;

import java.lang.reflect.Member;

/**
 * 雪花算法生成器
 *
 * @author george
 */
@RequiredArgsConstructor
public class SnowflakeIdGenerator implements IdentifierGenerator {

    /**
     * 雪花算法
     */
    private final SnowflakeId snowflakeId;

    /**
     * 成员
     */
    private final Member member;

    /**
     * 上下文
     */
    private final CustomIdGeneratorCreationContext context;

    /**
     * 生成id
     *
     * @param session 会话
     * @param object  对象
     * @return id
     */
    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        long workerId = snowflakeId.workerId();
        long datacenterId = snowflakeId.datacenterId();
        // 如果没有配置workerId和datacenterId，则使用默认的
        if (workerId == 0 && datacenterId == 0) {
            return IdUtil.getSnowflakeNextId();
        }
        return IdUtil.getSnowflake(workerId, datacenterId).nextId();
    }
}
