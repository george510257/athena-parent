package com.athena.cloud.sdk.excel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * 数据源类型转换器
 */
public class DatasourceTypeConverter implements Converter<Integer> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return switch (cellData.getStringValue()) {
            case "mysql" -> 1;
            case "oracle" -> 2;
            case "sqlserver" -> 3;
            case "postgresql" -> 4;
            default -> 0;
        };
    }

    @Override
    public WriteCellData<?> convertToExcelData(Integer value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return switch (value) {
            case 1 -> new WriteCellData<>("mysql");
            case 2 -> new WriteCellData<>("oracle");
            case 3 -> new WriteCellData<>("sqlserver");
            case 4 -> new WriteCellData<>("postgresql");
            default -> new WriteCellData<>("");
        };
    }

}
