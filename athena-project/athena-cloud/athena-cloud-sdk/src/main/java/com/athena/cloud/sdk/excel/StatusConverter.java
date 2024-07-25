package com.athena.cloud.sdk.excel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * 状态转换器
 */
public class StatusConverter implements Converter<Integer> {

    /**
     * 支持的Java类型
     *
     * @return Java类型
     */
    @Override
    public Class<?> supportJavaTypeKey() {
        return Integer.class;
    }

    /**
     * 支持的Excel类型
     *
     * @return Excel类型
     */
    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * 转换Excel数据到Java数据
     *
     * @param cellData            Excel数据
     * @param contentProperty     Excel属性
     * @param globalConfiguration 全局配置
     * @return Java数据
     * @throws Exception 异常
     */
    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return switch (cellData.getStringValue()) {
            case "启用" -> 1;
            case "禁用" -> 0;
            default -> 0;
        };
    }

    /**
     * 转换Java数据到Excel数据
     *
     * @param value               Java数据
     * @param contentProperty     Excel属性
     * @param globalConfiguration 全局配置
     * @return Excel数据
     * @throws Exception 异常
     */
    @Override
    public WriteCellData<?> convertToExcelData(Integer value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return switch (value) {
            case 1 -> new WriteCellData<>("启用");
            case 0 -> new WriteCellData<>("禁用");
            default -> new WriteCellData<>("");
        };
    }

}
