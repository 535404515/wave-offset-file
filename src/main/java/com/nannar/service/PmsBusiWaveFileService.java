package com.nannar.service;

import com.nannar.entity.PmsBusiWaveFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Caiyuhui
 * @date 2020/5/15
 * @time 9:54
 */
public interface PmsBusiWaveFileService {
    /**
     * 查找波形文件，准备入库
     * @param dbName
     * @param fileTypeId
     * @param offset
     * @param pageSize
     * @return
     */
    List<PmsBusiWaveFile> listFileForTempOffset(@Param(value="dbName") String dbName, @Param(value="fileTypeId") Integer fileTypeId, @Param(value="offset")Integer offset, @Param(value="pageSize") Integer pageSize);
    /**
     * 查找波形文件，准备入库。
     * @param dbName
     * @param fileTypeId
     * @param pageSize
     * @return
     */
    List<PmsBusiWaveFile> listFileForRead(@Param(value="dbName") String dbName,@Param(value="fileTypeId") Integer fileTypeId,@Param(value="pageSize") Integer pageSize);

    /**
     * 读取一个动态拉出值波形文件，并将数据插入到数据库中。
     * @param dbName
     * @param waveFile
     * @param stationMap
     * @return
     */
    Integer readWaveOffsetFile(String dbName, PmsBusiWaveFile waveFile, Map<String,String> stationMap);
    /**
     * 读取一个动态拉出值波形文件，并插入到临时表中。
     * @author    : hangui_zhang
     * @create by : 2019-10-21 14:41:08
     * @param dbName
     * @param waveFile
     * @return
     */
    Integer readFileToWaveOffsetDataTemp(String dbName, PmsBusiWaveFile waveFile);
}
