package com.nannar.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Caiyuhui
 * @date 2020/5/15
 * @time 15:05
 */
public interface PmsBusiAlarmWaveOffsetService {
    /**
     * 将告警记录中的新数据复制到告警&拉出值表中
     * @param dbName
     * @param fileLogId
     * @return
     */
    Integer makeDataFromAlarmLog(@Param(value="dbName") String dbName, @Param(value="fileLogId")Long fileLogId);
    /**
     * 查询temp表
     * @param fileLogId
     * @return
     */
    List<Map> selectTemp(String dbName,Long fileLogId);
}
