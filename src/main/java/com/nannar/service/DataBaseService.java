package com.nannar.service;

import com.nannar.entity.PmsSystemDatabase;

import java.util.List;

/**
 * @author Caiyuhui
 * @date 2020/5/15
 * @time 9:33
 */
public interface DataBaseService {
    /**
     * 查询所有数据库
     * @return
     */
    public List<PmsSystemDatabase> loadAlldatabases();
}
