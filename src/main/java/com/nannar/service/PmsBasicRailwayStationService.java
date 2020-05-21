package com.nannar.service;

import com.nannar.entity.PmsBasicRailwayStation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Caiyuhui
 * @date 2020/5/15
 * @time 10:11
 */
public interface PmsBasicRailwayStationService {
    List<PmsBasicRailwayStation> findAll(@Param(value="dbName")String dbName);
}
