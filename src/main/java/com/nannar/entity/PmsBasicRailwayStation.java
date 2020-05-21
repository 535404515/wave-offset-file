package com.nannar.entity;

import java.io.Serializable;


/**
 * @author yuhui_cai
 */
public class PmsBasicRailwayStation extends BasePojo{
    /**
     * 线路Id
     */
    private Integer lineId;
    /**
     * 站台Id
     */
    private Integer railwayStationId;
    /**
     * 站台名称
     */
    private String railwayStationName;


    /**
     * 设置 线路Id
     */
    public void setLineId(Integer lineId){
        this.lineId = lineId;
    }
    /**
     * 获取 线路Id
     */
    public Integer getLineId(){
        return this.lineId;
    }
    /**
     * 设置 站台Id
     */
    public void setRailwayStationId(Integer railwayStationId){
        this.railwayStationId = railwayStationId;
    }
    /**
     * 获取 站台Id
     */
    public Integer getRailwayStationId(){
        return this.railwayStationId;
    }
    /**
     * 设置 站台名称
     */
    public void setRailwayStationName(String railwayStationName){
        this.railwayStationName = railwayStationName == null ? null : railwayStationName.trim();
    }
    /**
     * 获取 站台名称
     */
    public String getRailwayStationName(){
        return this.railwayStationName;
    }


    @Override
    public Serializable getId() {
        // TODO Auto-generated method stub
        return getRailwayStationId();
    }
}
