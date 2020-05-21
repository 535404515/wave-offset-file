package com.nannar.dao;

import com.nannar.entity.PmsBasicRailwayStation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Caiyuhui
 * @date 2020/5/15
 * @time 10:11
 */
@Mapper
@Component("PmsBasicRailwayStationMapper")
public interface PmsBasicRailwayStationMapper {
    List<PmsBasicRailwayStation> findAll(@Param(value="dbName")String dbName);
}
