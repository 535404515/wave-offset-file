package com.nannar.dao;
import	java.util.Map;

import com.nannar.entity.PmsBusiWaveOffsetDataTemp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author CaiMapper * @date 2020/5/15
 * @time 14:57
 */
@Mapper
@Component
public interface PmsBusiWaveOffsetDataTempMapper {
    Integer insertSelectiveBatch(@Param(value="dbName") String dbName, @Param(value="records") List<PmsBusiWaveOffsetDataTemp> records);
}
