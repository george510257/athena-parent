package com.athena.upms.sdk.excel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * 状态转换器
 */
public class StatusConverter implements Converter<Boolean> {

    /**
     * 支持的Java类型
     *
     * @return Java类型
     */
    @Override
    public Class<?> supportJavaTypeKey() {
        return Boolean.class;
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
     * 转换为Java数据
     *
     * @param cellData            Cell数据
     * @param contentProperty     内容属性
     * @param globalConfiguration 全局配置
     * @return Java数据
     * @throws Exception 异常
     */
    @Override
    public Boolean convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return cellData.getStringValue().equals("禁用");
    }

    /**
     * 转换为Excel数据
     *
     * @param value               Java数据
     * @param contentProperty     内容属性
     * @param globalConfiguration 全局配置
     * @return Excel数据
     * @throws Exception 异常
     */
    @Override
    public WriteCellData<?> convertToExcelData(Boolean value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return value ? new WriteCellData<>("禁用") : new WriteCellData<>("正常");
    }

}
