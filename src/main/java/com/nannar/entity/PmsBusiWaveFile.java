package com.nannar.entity;

import java.io.Serializable;

/**
 * @author Caiyuhui
 * @date 2020/5/15
 * @time 9:41
 */
public class PmsBusiWaveFile extends BasePojo {
    /**
     * 波型文件状态 未读取：0
     */
    public static final Integer FILE_STATE_READY = 0;
    /**
     * 波型文件状态  入库完成：1
     */
    public static final Integer FILE_STATE_READ_FINISHED = 1;
    /**
     * 波型文件状态  文件缺失：2
     */
    public static final Integer FILE_STATE_LOSTED = 2;
    /**
     * 波型文件状态  文件解析错误：99
     */
    public static final Integer FILE_STATE_FAILED = 99;

    /**
     * 波形文件id，自增
     */
    private Long fileLogId;
    /**
     * 监测站编号
     */
    private Integer monitorStationId;
    /**
     * 监测站附加编号
     */
    private Integer monitorStationIdEx;
    /**
     * 当前站
     */
    private Integer currentStation;
    /**
     * 下一站
     */
    private Integer nextStation;
    /**
     * 行车方向
     */
    private Byte direction;
    /**
     * 文件类型
     */
    private Integer fileTypeId;
    /**
     * 文件目录
     */
    private String remoteDir;
    /**
     * 文件目录
     */
    private String localDir;
    /**
     * 文件传输状态
     */
    private Byte transferSuccess;
    /**
     * 时间
     */
    private java.sql.Timestamp time;
    /**
     * 文件数据起始时间
     */
    private java.sql.Timestamp offsetStartTime;
    /**
     * 文件数据截止时间
     */
    private java.sql.Timestamp offsetEndTime;
    /**
     * 文件数据采集点数
     */
    private Integer offsetSamples;
    /**
     * 文件状态。1：已入库，0：未入库，2：文件缺失，99：文件解析错误
     */
    private Integer fileState;
    /**
     * 站点区间名称
     */
    private String sectionName;

    /**
     * 获取 站点区间名称
     * @return sectionName
     */
    public String getSectionName() {
        return sectionName;
    }
    /**
     * 设置 站点区间名称
     * @param sectionName 站点区间名称
     */
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
    /**
     * 获取 文件数据采集点数
     * @return offsetSamples
     */
    public Integer getOffsetSamples() {
        return offsetSamples;
    }
    /**
     * 设置 文件数据采集点数
     * @param offsetSamples 文件数据采集点数
     */
    public void setOffsetSamples(Integer offsetSamples) {
        this.offsetSamples = offsetSamples;
    }
    /**
     * 获取 文件状态。1：已入库，0：未入库，2：文件缺失，99：文件解析错误
     * @return fileState
     */
    public Integer getFileState() {
        return fileState;
    }
    /**
     * 设置 文件状态。1：已入库，0：未入库，2：文件缺失，99：文件解析错误
     * @param fileState 文件状态。1：已入库，0：未入库，2：文件缺失，99：文件解析错误
     */
    public void setFileState(Integer fileState) {
        this.fileState = fileState;
    }
    /**
     * 获取 文件数据起始时间
     * @return offsetStartTime
     */
    public java.sql.Timestamp getOffsetStartTime() {
        return offsetStartTime;
    }
    /**
     * 设置 文件数据起始时间
     * @param offsetStartTime 文件数据起始时间
     */
    public void setOffsetStartTime(java.sql.Timestamp offsetStartTime) {
        this.offsetStartTime = offsetStartTime;
    }
    /**
     * 获取 文件数据截止时间
     * @return offsetEndTime
     */
    public java.sql.Timestamp getOffsetEndTime() {
        return offsetEndTime;
    }
    /**
     * 设置 文件数据截止时间
     * @param offsetEndTime 文件数据截止时间
     */
    public void setOffsetEndTime(java.sql.Timestamp offsetEndTime) {
        this.offsetEndTime = offsetEndTime;
    }

    /**
     * 设置 波形文件id，自增
     */
    public void setFileLogId(Long fileLogId){
        this.fileLogId = fileLogId;
    }
    /**
     * 获取 波形文件id，自增
     */
    public Long getFileLogId(){
        return this.fileLogId;
    }
    /**
     * 设置 监测站编号
     */
    public void setMonitorStationId(Integer monitorStationId){
        this.monitorStationId = monitorStationId;
    }
    /**
     * 获取 监测站编号
     */
    public Integer getMonitorStationId(){
        return this.monitorStationId;
    }

    /**
     * 获取 监测站附加编号
     * @return monitorStationIdEx
     */
    public Integer getMonitorStationIdEx() {
        return monitorStationIdEx;
    }
    /**
     * 设置 监测站附加编号
     * @param monitorStationIdEx 监测站附加编号
     */
    public void setMonitorStationIdEx(Integer monitorStationIdEx) {
        this.monitorStationIdEx = monitorStationIdEx;
    }
    /**
     * 设置 当前站
     */
    public void setCurrentStation(Integer currentStation){
        this.currentStation = currentStation;
    }
    /**
     * 获取 当前站
     */
    public Integer getCurrentStation(){
        return this.currentStation;
    }
    /**
     * 设置 下一站
     */
    public void setNextStation(Integer nextStation){
        this.nextStation = nextStation;
    }
    /**
     * 获取 下一站
     */
    public Integer getNextStation(){
        return this.nextStation;
    }
    /**
     * 设置 行车方向
     */
    public void setDirection(Byte direction){
        this.direction = direction;
    }
    /**
     * 获取 行车方向
     */
    public Byte getDirection(){
        return this.direction;
    }
    /**
     * 设置 文件类型
     */
    public void setFileTypeId(Integer fileTypeId){
        this.fileTypeId = fileTypeId;
    }
    /**
     * 获取 文件类型
     */
    public Integer getFileTypeId(){
        return this.fileTypeId;
    }
    /**
     * 设置 文件目录
     */
    public void setRemoteDir(String remoteDir){
        this.remoteDir = remoteDir == null ? null : remoteDir.trim();
    }
    /**
     * 获取 文件目录
     */
    public String getRemoteDir(){
        return this.remoteDir;
    }
    /**
     * 设置 文件目录
     */
    public void setLocalDir(String localDir){
        this.localDir = localDir == null ? null : localDir.trim();
    }
    /**
     * 获取 文件目录
     */
    public String getLocalDir(){
        return this.localDir;
    }
    /**
     * 设置 文件传输状态
     */
    public void setTransferSuccess(Byte transferSuccess){
        this.transferSuccess = transferSuccess;
    }
    /**
     * 获取 文件传输状态
     */
    public Byte getTransferSuccess(){
        return this.transferSuccess;
    }
    /**
     * 设置 时间
     */
    public void setTime(java.sql.Timestamp time){
        this.time = time;
    }
    /**
     * 获取 时间
     */
    public java.sql.Timestamp getTime(){
        return this.time;
    }


    @Override
    public Serializable getId() {
        return this.getFileLogId();
    }
}
