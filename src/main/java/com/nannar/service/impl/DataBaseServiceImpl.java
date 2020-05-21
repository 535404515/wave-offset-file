package com.nannar.service.impl;

import com.nannar.dao.DataBaseMapper;
import com.nannar.entity.PmsSystemDatabase;
import com.nannar.service.DataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Caiyuhui
 * @date 2020/5/15
 * @time 9:34
 */
@Service("DataBaseServiceImpl")
public class DataBaseServiceImpl implements DataBaseService {
    private final DataBaseMapper mapper;
    @Autowired
    public DataBaseServiceImpl(DataBaseMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<PmsSystemDatabase> loadAlldatabases() {
        return mapper.loadAlldatabases();
    }
}
