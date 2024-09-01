package com.athena.starter.excel.strategy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.cell.CellUtil;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

/**
 * 列合并策略 <br> 纵向合并相同列的单元格 <br> 例如：合并第1列和第2列相同的单元格
 *
 * @author george
 */
public class ColumnMergeStrategy extends AbstractMergeStrategy {
    /**
     * 忽略的值
     */
    private final List<Object> ignoreValues;
    /**
     * 合并列索引
     */
    private final List<Integer> columnIndexes;

    public ColumnMergeStrategy(List<Integer> columnIndexes, Object... ignoreValues) {
        this.columnIndexes = columnIndexes;
        this.ignoreValues = CollUtil.newArrayList(ignoreValues);
    }

    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex) {
        // 获取当前单元格的行索引和列索引
        int rowIndex = cell.getRowIndex();
        int columnIndex = cell.getColumnIndex();
        // 如果当前单元格的值在忽略的值中，则不合并单元格
        if (ignoreValues.contains(CellUtil.getCellValue(cell))) {
            return;
        }
        if (columnIndexes.contains(columnIndex)) {
            mergeCell(sheet, cell, rowIndex, columnIndex);
        }
    }

    private void mergeCell(Sheet sheet, Cell cell, int rowIndex, int columnIndex) {
        // 获取上一行的单元格
        Cell preCell = sheet.getRow(rowIndex - 1).getCell(columnIndex);
        // 获取上一行的单元格的值
        Object preCellValue = CellUtil.getCellValue(preCell);
        // 获取当前单元格的值
        Object cellValue = CellUtil.getCellValue(cell);
        // 如果当前单元格的值和上一行的单元格的值相同，则合并单元格
        if (preCellValue.equals(cellValue)) {
            // 获取上一个单元格的合并单元格
            Cell firstCell = CellUtil.getMergedRegionCell(preCell);
            // 移除合并单元格
            removeMergedRegion(sheet, firstCell);
            // 合并单元格
            CellUtil.mergingCells(sheet, firstCell.getRowIndex(), rowIndex, columnIndex, columnIndex);
        }
    }

    private void removeMergedRegion(Sheet sheet, Cell cell) {
        List<CellRangeAddress> list = sheet.getMergedRegions();
        for (int i = 0; i < list.size(); i++) {
            CellRangeAddress cellRangeAddress = list.get(i);
            if (cellRangeAddress.isInRange(cell)) {
                sheet.removeMergedRegion(i);
            }
        }
    }

}
