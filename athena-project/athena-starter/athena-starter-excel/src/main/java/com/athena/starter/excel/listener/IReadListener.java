package com.athena.starter.excel.listener;

import com.alibaba.excel.read.listener.ReadListener;
import com.athena.starter.excel.support.ExcelErrorMessage;

import java.util.List;

public interface IReadListener<T> extends ReadListener<T> {

    /**
     * 获取对象列表
     */
    List<T> getList();

    /**
     * 获取异常校验结果
     */
    List<ExcelErrorMessage> getErrors();
}
