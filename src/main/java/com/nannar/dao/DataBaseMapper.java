package com.nannar.dao;

import com.nannar.entity.PmsSystemDatabase;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Caiyuhui
 * @date 2020/5/15
 * @time 9:24
 */
@Mapper
@Component("DataBaseMapper")
public interface DataBaseMapper {
    /**
     * 查询所有数据库
     * @return
     */
    public List<PmsSystemDatabase> loadAlldatabases();
}
