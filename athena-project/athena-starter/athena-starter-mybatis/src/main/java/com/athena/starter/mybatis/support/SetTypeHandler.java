package com.athena.starter.mybatis.support;

import cn.hutool.core.util.StrUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Set类型处理器
 *
 * @param <V> value
 * @author george
 */
@Component
@MappedTypes({Set.class, LinkedHashSet.class, HashSet.class, TreeSet.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class SetTypeHandler<V> extends BaseTypeHandler<Set<V>> {
    /**
     * 设置非空参数
     *
     * @param ps        数据库操作对象
     * @param i         参数索引
     * @param parameter 参数
     * @param jdbcType  jdbc类型
     * @throws SQLException sql异常
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Set<V> parameter, JdbcType jdbcType) throws SQLException {
        // set转字符串
        String value = parameter.stream().map(StrUtil::toString).collect(Collectors.joining(","));
        // 设置参数
        ps.setString(i, value);
    }

    /**
     * 获取可空结果
     *
     * @param rs         结果集
     * @param columnName 列名
     * @return 结果
     * @throws SQLException sql异常
     */
    @Override
    public Set<V> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String result = rs.getString(columnName);
        if (StrUtil.isNotEmpty(result)) {
            return StrUtil.split(result, ",").stream().map(v -> (V) v).collect(Collectors.toSet());
        }
        return null;
    }

    /**
     * 获取可空结果
     *
     * @param rs          结果集
     * @param columnIndex 列索引
     * @return 结果
     * @throws SQLException sql异常
     */
    @Override
    public Set<V> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String result = rs.getString(columnIndex);
        if (StrUtil.isNotEmpty(result)) {
            return StrUtil.split(result, ",").stream().map(v -> (V) v).collect(Collectors.toSet());
        }
        return null;
    }

    /**
     * 获取可空结果
     *
     * @param cs          结果集
     * @param columnIndex 列索引
     * @return 结果
     * @throws SQLException sql异常
     */
    @Override
    public Set<V> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String result = cs.getString(columnIndex);
        if (StrUtil.isNotEmpty(result)) {
            return StrUtil.split(result, ",").stream().map(v -> (V) v).collect(Collectors.toSet());
        }
        return null;
    }
}
