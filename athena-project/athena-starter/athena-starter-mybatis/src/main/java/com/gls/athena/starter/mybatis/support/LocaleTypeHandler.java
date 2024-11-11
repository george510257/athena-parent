package com.gls.athena.starter.mybatis.support;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

/**
 * 语言类型处理器
 *
 * @author george
 */
@Component
@MappedTypes(Locale.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class LocaleTypeHandler extends BaseTypeHandler<Locale> {
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
    public void setNonNullParameter(PreparedStatement ps, int i, Locale parameter, JdbcType jdbcType) throws SQLException {
        // 设置参数
        ps.setString(i, parameter.toLanguageTag());
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
    public Locale getNullableResult(ResultSet rs, String columnName) throws SQLException {
        // 获取字符串
        String result = rs.getString(columnName);
        if (result != null) {
            return Locale.forLanguageTag(result);
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
    public Locale getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        // 获取字符串
        String result = rs.getString(columnIndex);
        if (result != null) {
            return Locale.forLanguageTag(result);
        }
        return null;
    }

    /**
     * 获取可空结果
     *
     * @param cs          数据库操作对象
     * @param columnIndex 列索引
     * @return 结果
     * @throws SQLException sql异常
     */
    @Override
    public Locale getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        // 获取字符串
        String result = cs.getString(columnIndex);
        if (result != null) {
            return Locale.forLanguageTag(result);
        }
        return null;
    }
}
