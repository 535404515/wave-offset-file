package com.nannar.service.impl;

import com.nannar.dao.PmsBusiAlarmWaveOffsetMapper;
import com.nannar.service.PmsBusiAlarmWaveOffsetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Caiyuhui
 * @date 2020/5/15
 * @time 15:06
 */
@Service
public class PmsBusiAlarmWaveOffsetServiceImpl implements PmsBusiAlarmWaveOffsetService {
    private final PmsBusiAlarmWaveOffsetMapper mapper;

    public PmsBusiAlarmWaveOffsetServiceImpl(PmsBusiAlarmWaveOffsetMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Integer makeDataFromAlarmLog(String dbName, Long fileLogId) {
        return mapper.makeDataFromAlarmLog(dbName,fileLogId);
    }

    @Override
    public List<Map> selectTemp(String dbName,Long fileLogId) {
        return mapper.selectTemp(dbName,fileLogId);
    }
}
