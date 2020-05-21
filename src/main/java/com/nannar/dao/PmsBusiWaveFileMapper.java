package com.nannar.dao;

import com.nannar.entity.PmsBusiWaveFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Caiyuhui
 * @date 2020/5/15
 * @time 9:48
 */
@Mapper
@Component("PmsBusiWaveFileMapper")
public interface PmsBusiWaveFileMapper {
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
//    Integer readWaveOffsetFile(String dbName, PmsBusiWaveFile waveFile, Map<String,String> stationMap);
    /**
     * 根据主键执行update，语句中仅包含非null字段。
     * @author        : hangui_zhang
     * @create by     : 2018年12月12日 下午5:30:35
     * @param record
     * @param dbName
     * @return
     */
    int updateByPrimaryKeySelective(@Param(value="record") PmsBusiWaveFile record,@Param(value="dbName") String dbName);
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
