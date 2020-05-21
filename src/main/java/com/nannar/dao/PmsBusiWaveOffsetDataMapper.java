package com.nannar.dao;

import com.nannar.entity.PmsBusiWaveOffsetData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Caiyuhui
 * @date 2020/5/15
 * @time 11:21
 */
@Mapper
@Component("PmsBusiWaveOffsetDataMapper")
public interface PmsBusiWaveOffsetDataMapper {
    /**
     * insertSelectiveBatch
     * @param dbName
     * @param records
     * @return
     */
    Integer insertSelectiveBatch(@Param(value="dbName") String dbName,@Param(value="records") List<PmsBusiWaveOffsetData> records);
}
