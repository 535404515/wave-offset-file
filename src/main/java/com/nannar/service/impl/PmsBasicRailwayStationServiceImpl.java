package com.nannar.service.impl;

import com.nannar.dao.PmsBasicRailwayStationMapper;
import com.nannar.entity.PmsBasicRailwayStation;
import com.nannar.service.PmsBasicRailwayStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Caiyuhui
 * @date 2020/5/15
 * @time 10:12
 */
@Service("PmsBasicRailwayStationServiceImpl")
public class PmsBasicRailwayStationServiceImpl implements PmsBasicRailwayStationService {
    private final PmsBasicRailwayStationMapper mapper;
    @Autowired
    public PmsBasicRailwayStationServiceImpl(PmsBasicRailwayStationMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<PmsBasicRailwayStation> findAll(String dbName) {
        return mapper.findAll(dbName);
    }
}
