package com.gls.athena.starter.json.support;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 默认日期格式化类
 *
 * @author george
 */
public class DefaultDateFormat extends SimpleDateFormat {

    /**
     * 构造
     */
    public DefaultDateFormat() {
        super(DatePattern.NORM_DATETIME_PATTERN);
    }

    /**
     * 转换
     *
     * @param text 日期字符串
     * @param pos  ParsePosition 位置
     * @return 日期
     */
    @Override
    public Date parse(String text, ParsePosition pos) {
        return DateUtil.parse(text);
    }

    /**
     * 解析日期
     *
     * @param source 日期字符串
     * @return 日期
     * @throws ParseException 解析异常
     */
    @Override
    public Date parse(String source) throws ParseException {
        return DateUtil.parse(source);
    }
}
