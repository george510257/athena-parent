package com.athena.starter.excel.strategy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.cell.CellUtil;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

/**
 * 行合并策略 <br> 横向合并相同行的单元格
 *
 * @author george
 */
public class RowMergeStrategy extends AbstractMergeStrategy {

    /**
     * 合并列索引
     */
    private final Integer columnIndex;
    /**
     * 合并行的值
     */
    private final List<Object> rowValues;

    public RowMergeStrategy(Integer columnIndex, Object... rowValues) {
        this.columnIndex = columnIndex;
        this.rowValues = CollUtil.newArrayList(rowValues);
    }

    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex) {
        // 获取当前单元格的行索引和列索引
        int rowIndex = cell.getRowIndex();
        int columnIndex = cell.getColumnIndex();
        Object cellValue = CellUtil.getCellValue(cell);
        if (columnIndex < this.columnIndex && rowValues.contains(cellValue)) {
            if (!CellUtil.isMergedRegion(cell)) {
                CellUtil.mergingCells(sheet, rowIndex, rowIndex, columnIndex, this.columnIndex);
            }
        }
    }

}
