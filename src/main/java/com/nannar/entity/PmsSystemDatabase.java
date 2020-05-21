package com.nannar.entity;

import java.io.Serializable;

/**
 * @author Caiyuhui
 * @date 2020/5/15
 * @time 9:42
 */
public class PmsSystemDatabase extends BasePojo {
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[")
                .append("lineId="+this.lineId)
                .append(",lineName="+this.lineName)
                .append(",trainId="+this.trainId)
                .append(",trainNo="+this.trainNo)
                .append(",trainTypeId="+this.trainTypeId)
                .append(",trainTypeName="+this.trainTypeName)
                .append(",monitorStationId="+this.monitorStationId)
                .append(",deviceCode="+this.deviceCode)
                .append(",monitorStationName="+this.monitorStationName)
                .append(",monitorStationDatabase="+this.monitorStationDatabase)
                .append(",imgPath="+this.imgPath)
                .append(",ext1="+this.ext1)
                .append(",ext2="+this.ext2)
                .append(",ext3="+this.ext3)
                .append(",trainNoAlias="+this.trainNoAlias)
                .append("]");
        return sb.toString();
    }


    /**
     * @author        : hangui_zhang
     * @create by     : 2019-03-09 14:51:14
     * @param lineId 路线ID
     * @param lineName 路线名称
     * @param trainId 列车ID
     * @param trainNo 车号
     * @param trainTypeId 列车类型ID
     * @param trainTypeName 列车类型
     * @param monitorStationId 监测站ID
     * @param deviceCode 设备编号
     * @param monitorStationName 监测站名称
     * @param monitorStationDatabase 监测站库名
     * @param imgPath 列车照片
     * @param ext1 备用字段1
     * @param ext2 备用字段2
     * @param ext3 备用字段3
     * @param trainNoAlias 车号别名，可用于web选择设备库
     */
    public PmsSystemDatabase(Integer lineId, String lineName, Integer trainId,
                             String trainNo, Integer trainTypeId, String trainTypeName, Integer monitorStationId,
                             String deviceCode, String monitorStationName, String monitorStationDatabase, String imgPath,
                             String ext1, String ext2, String ext3, String trainNoAlias) {
        this.lineId = lineId;
        this.lineName = lineName;
        this.trainId = trainId;
        this.trainNo = trainNo;
        this.trainTypeId = trainTypeId;
        this.trainTypeName = trainTypeName;
        this.monitorStationId = monitorStationId;
        this.deviceCode = deviceCode;
        this.monitorStationName = monitorStationName;
        this.monitorStationDatabase = monitorStationDatabase;
        this.imgPath = imgPath;
        this.ext1 = ext1;
        this.ext2 = ext2;
        this.ext3 = ext3;
        this.trainNoAlias = trainNoAlias;

    }


    /**
     * @author        : hangui_zhang
     * @create by     : 2019-03-09 14:51:14
     */
    public PmsSystemDatabase() {
        super();
    }


    /**
     * 路线ID
     */
    private Integer lineId;
    /**
     * 路线名称
     */
    private String lineName;
    /**
     * 列车ID
     */
    private Integer trainId;
    /**
     * 车号
     */
    private String trainNo;
    /**
     * 列车类型ID
     */
    private Integer trainTypeId;
    /**
     * 列车类型
     */
    private String trainTypeName;
    /**
     * 监测站ID
     */
    private Integer monitorStationId;
    /**
     * 设备编号
     */
    private String deviceCode;
    /**
     * 监测站名称
     */
    private String monitorStationName;
    /**
     * 监测站库名
     */
    private String monitorStationDatabase;
    /**
     * 列车照片
     */
    private String imgPath;
    /**
     * 备用字段1
     */
    private String ext1;
    /**
     * 备用字段2
     */
    private String ext2;
    /**
     * 备用字段3
     */
    private String ext3;
    /**
     * 车号别名，可用于web选择设备库
     */
    private String trainNoAlias;


    /**
     * 设置 路线ID
     */
    public void setLineId(Integer lineId){
        this.lineId = lineId;
    }
    /**
     * 获取 路线ID
     */
    public Integer getLineId(){
        return this.lineId;
    }
    /**
     * 设置 路线名称
     */
    public void setLineName(String lineName){
        this.lineName = lineName == null ? null : lineName.trim();
    }
    /**
     * 获取 路线名称
     */
    public String getLineName(){
        return this.lineName;
    }
    /**
     * 设置 列车ID
     */
    public void setTrainId(Integer trainId){
        this.trainId = trainId;
    }
    /**
     * 获取 列车ID
     */
    public Integer getTrainId(){
        return this.trainId;
    }
    /**
     * 设置 车号
     */
    public void setTrainNo(String trainNo){
        this.trainNo = trainNo == null ? null : trainNo.trim();
    }
    /**
     * 获取 车号
     */
    public String getTrainNo(){
        return this.trainNo;
    }
    /**
     * 设置 列车类型ID
     */
    public void setTrainTypeId(Integer trainTypeId){
        this.trainTypeId = trainTypeId;
    }
    /**
     * 获取 列车类型ID
     */
    public Integer getTrainTypeId(){
        return this.trainTypeId;
    }
    /**
     * 设置 列车类型
     */
    public void setTrainTypeName(String trainTypeName){
        this.trainTypeName = trainTypeName == null ? null : trainTypeName.trim();
    }
    /**
     * 获取 列车类型
     */
    public String getTrainTypeName(){
        return this.trainTypeName;
    }
    /**
     * 设置 监测站ID
     */
    public void setMonitorStationId(Integer monitorStationId){
        this.monitorStationId = monitorStationId;
    }
    /**
     * 获取 监测站ID
     */
    public Integer getMonitorStationId(){
        return this.monitorStationId;
    }
    /**
     * 设置 设备编号
     */
    public void setDeviceCode(String deviceCode){
        this.deviceCode = deviceCode == null ? null : deviceCode.trim();
    }
    /**
     * 获取 设备编号
     */
    public String getDeviceCode(){
        return this.deviceCode;
    }
    /**
     * 设置 监测站名称
     */
    public void setMonitorStationName(String monitorStationName){
        this.monitorStationName = monitorStationName == null ? null : monitorStationName.trim();
    }
    /**
     * 获取 监测站名称
     */
    public String getMonitorStationName(){
        return this.monitorStationName;
    }
    /**
     * 设置 监测站库名
     */
    public void setMonitorStationDatabase(String monitorStationDatabase){
        this.monitorStationDatabase = monitorStationDatabase == null ? null : monitorStationDatabase.trim();
    }
    /**
     * 获取 监测站库名
     */
    public String getMonitorStationDatabase(){
        return this.monitorStationDatabase;
    }
    /**
     * 设置 列车照片
     */
    public void setImgPath(String imgPath){
        this.imgPath = imgPath == null ? null : imgPath.trim();
    }
    /**
     * 获取 列车照片
     */
    public String getImgPath(){
        return this.imgPath;
    }
    /**
     * 设置 备用字段1
     */
    public void setExt1(String ext1){
        this.ext1 = ext1 == null ? null : ext1.trim();
    }
    /**
     * 获取 备用字段1
     */
    public String getExt1(){
        return this.ext1;
    }
    /**
     * 设置 备用字段2
     */
    public void setExt2(String ext2){
        this.ext2 = ext2 == null ? null : ext2.trim();
    }
    /**
     * 获取 备用字段2
     */
    public String getExt2(){
        return this.ext2;
    }
    /**
     * 设置 备用字段3
     */
    public void setExt3(String ext3){
        this.ext3 = ext3 == null ? null : ext3.trim();
    }
    /**
     * 获取 备用字段3
     */
    public String getExt3(){
        return this.ext3;
    }
    /**
     * 设置 车号别名，可用于web选择设备库
     */
    public void setTrainNoAlias(String trainNoAlias){
        this.trainNoAlias = trainNoAlias == null ? null : trainNoAlias.trim();
    }
    /**
     * 获取 车号别名，可用于web选择设备库
     */
    public String getTrainNoAlias(){
        return this.trainNoAlias;
    }


    @Override
    public Serializable getId() {
        return this.lineId + ","+this.trainId+","+this.monitorStationId;
    }
}
