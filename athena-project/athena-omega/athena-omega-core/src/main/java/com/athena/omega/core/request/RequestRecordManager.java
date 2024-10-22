package com.athena.omega.core.request;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求记录管理器
 *
 * @author george
 */
@Slf4j
public class RequestRecordManager {
    /**
     * 请求记录
     */
    private final Map<String, RequestRecord> records = new HashMap<>();
    /**
     * 归档记录
     */
    private final List<RequestRecord> archiveRecords = new ArrayList<>();

    /**
     * 获取请求记录条数
     *
     * @return 请求记录条数
     */
    public int getRecordCount() {
        return records.size();
    }

    /**
     * 获取归档记录条数
     *
     * @return 归档记录条数
     */
    public int getArchiveRecordCount() {
        return archiveRecords.size();
    }

    /**
     * 获取请求记录
     *
     * @param uuid 唯一标识
     * @return 请求记录
     */
    public RequestRecord getRecord(String uuid) {
        if (!records.containsKey(uuid)) {
            records.put(uuid, new RequestRecord().setUuid(uuid));
        }
        return records.get(uuid);
    }

    /**
     * 删除请求记录
     *
     * @param uuid 唯一标识
     */
    public void removeRecord(String uuid) {
        records.remove(uuid);
    }

    /**
     * 删除请求记录
     *
     * @param record 请求记录
     */
    public void removeRecord(RequestRecord record) {
        records.remove(record.getUuid());
    }

    /**
     * 归档请求记录
     *
     * @param uuid 唯一标识
     */
    public void archiveRecord(String uuid) {
        RequestRecord record = records.remove(uuid);
        // 归档记录
        if (record.isArchive() || record.hasErrorLog()) {
            archiveRecords.add(record);
        }
    }

    /**
     * 归档请求记录
     *
     * @param record 请求记录
     */
    public void archiveRecord(RequestRecord record) {
        archiveRecord(record.getUuid());
    }

    /**
     * 出栈归档记录 先进先出
     *
     * @return 归档记录
     */
    public RequestRecord popArchiveRecord() {
        if (!archiveRecords.isEmpty()) {
            return archiveRecords.removeFirst();
        }
        return null;
    }

    /**
     * 清理请求记录
     *
     * @param time 时间
     */
    public void clearRecord(long time) {
        records.values().stream()
                .filter(record -> System.currentTimeMillis() - record.getRequestTime() > time)
                .forEach(this::archiveRecord);
    }

}
