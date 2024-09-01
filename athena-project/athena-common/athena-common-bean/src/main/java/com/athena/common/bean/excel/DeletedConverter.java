package com.athena.common.bean.excel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * 删除标记转换器
 *
 * @author george
 */
public class DeletedConverter implements Converter<Boolean> {

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
     * @param cellData            Excel单元格数据
     * @param contentProperty     内容属性
     * @param globalConfiguration 全局配置
     * @return Java数据
     */
    @Override
    public Boolean convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        return "已删除".equals(cellData.getStringValue());
    }

    /**
     * 转换为Excel数据
     *
     * @param value               Java数据
     * @param contentProperty     内容属性
     * @param globalConfiguration 全局配置
     * @return Excel数据
     */
    @Override
    public WriteCellData<?> convertToExcelData(Boolean value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        return value ? new WriteCellData<>("已删除") : new WriteCellData<>("正常");
    }

}
