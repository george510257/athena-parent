package com.athena.starter.excel.listener;

import cn.hutool.extra.validation.BeanValidationResult;
import cn.hutool.extra.validation.ValidationUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.Cell;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.util.ConverterUtils;
import com.athena.starter.excel.annotation.ExcelLine;
import com.athena.starter.excel.annotation.ExcelMultiColumn;
import com.athena.starter.excel.support.ExcelErrorMessage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 默认读取监听器
 *
 * @param <T> 读取对象
 * @author george
 */
@Slf4j
@Getter
public class DefaultReadListener<T> implements IReadListener<T> {
    /**
     * 对象列表
     */
    private final List<T> list = new ArrayList<>();
    /**
     * 错误信息
     */
    private final List<ExcelErrorMessage> errors = new ArrayList<>();
    /**
     * 头信息
     */
    private final Map<Integer, String> headMap = new HashMap<>();

    /**
     * 解析异常
     *
     * @param exception 异常
     * @param context   上下文
     * @throws Exception 异常
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        log.error("解析异常", exception);
    }

    /**
     * 解析头数据
     *
     * @param headMap 头数据
     * @param context 上下文
     */
    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        this.headMap.putAll(ConverterUtils.convertToStringMap(headMap, context));
        log.info("解析到一条头数据: {}", this.headMap);
    }

    /**
     * 解析数据
     *
     * @param data    数据
     * @param context 上下文
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        Integer rowIndex = context.readRowHolder().getRowIndex();
        Map<Integer, Cell> cellDataMap = context.readRowHolder().getCellMap();
        log.info("解析第{}行数据: {}", rowIndex, JSONUtil.toJsonStr(cellDataMap));
        Arrays.stream(data.getClass().getDeclaredFields()).forEach(field -> {
            // 设置行号
            setExcelLine(data, field, rowIndex);
            // 设置多列
            setExcelMultiColumn(data, field, cellDataMap);
        });
        // 校验数据
        validate(data, rowIndex);
        // 添加数据
        list.add(data);
    }

    /**
     * 校验数据
     *
     * @param data     数据
     * @param rowIndex 行号
     */
    private void validate(T data, Integer rowIndex) {
        BeanValidationResult result = ValidationUtil.warpValidate(data);
        if (!result.isSuccess()) {
            result.getErrorMessages().forEach(errorMessage -> errors.add(new ExcelErrorMessage()
                    .setLine(rowIndex)
                    .setFieldName(errorMessage.getPropertyName())
                    .setErrorMessage(errorMessage.getMessage())
                    .setErrorValue(errorMessage.getValue())));
        }
    }

    /**
     * 设置多列
     *
     * @param data    数据
     * @param field   字段
     * @param cellMap 单元格数据
     */
    private void setExcelMultiColumn(T data, Field field, Map<Integer, Cell> cellMap) {
        if (field.isAnnotationPresent(ExcelMultiColumn.class) && field.getType().equals(Map.class)) {
            try {
                field.setAccessible(true);
                ExcelMultiColumn excelMultiColumn = field.getAnnotation(ExcelMultiColumn.class);
                int start = excelMultiColumn.start();
                int end = Math.min(excelMultiColumn.end(), headMap.size());
                Map<String, Object> multiColumnMap = new HashMap<>(end - start);
                for (int i = start; i < end; i++) {
                    multiColumnMap.put(headMap.get(i), getJavaValue(cellMap.get(i)));
                }
                field.set(data, multiColumnMap);
            } catch (IllegalAccessException e) {
                log.error("设置多列失败", e);
            }
        }
    }

    /**
     * 获取Java值
     *
     * @param cell 单元格
     * @return Java值
     */
    private Object getJavaValue(Cell cell) {
        if (cell instanceof ReadCellData<?> readCellData) {
            return switch (readCellData.getType()) {
                case STRING -> readCellData.getStringValue();
                case BOOLEAN -> readCellData.getBooleanValue();
                case NUMBER -> readCellData.getNumberValue();
                default -> readCellData.getData();
            };
        }
        return null;
    }

    /**
     * 设置行号
     *
     * @param data     数据
     * @param field    字段
     * @param rowIndex 行号
     */
    private void setExcelLine(T data, Field field, Integer rowIndex) {
        if (field.isAnnotationPresent(ExcelLine.class) && field.getType().equals(Integer.class)) {
            try {
                field.setAccessible(true);
                field.set(data, rowIndex);
            } catch (IllegalAccessException e) {
                log.error("设置行号失败", e);
            }
        }
    }

    /**
     * 解析额外数据
     *
     * @param extra   额外数据
     * @param context 上下文
     */
    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        log.info("解析到一条额外数据: {}", extra);
    }

    /**
     * 解析完成
     *
     * @param context 上下文
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成！");
    }

}
