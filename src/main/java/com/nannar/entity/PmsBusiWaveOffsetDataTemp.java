package com.nannar.entity;


import java.io.Serializable;
import java.sql.Timestamp;
public class PmsBusiWaveOffsetDataTemp extends BasePojo{
    /**
     *
     */
    private PmsBusiWaveOffsetDataTemp() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @param offsetId
     * @param fileLogId
     * @param distance
     * @param offsetTime
     * @param offsetValue
     * @param offsetValueOri
     */
    private PmsBusiWaveOffsetDataTemp(String offsetId,
                                      Long fileLogId,
                                      Integer distance,
                                      Timestamp offsetTime,
                                      Integer offsetValue,
                                      Double offsetValueOri) {
        super();
        this.offsetId = offsetId;
        this.fileLogId = fileLogId;
        this.distance = distance;
        this.offsetTime = offsetTime;
        this.offsetValue = offsetValue;
        this.offsetValueOri = offsetValueOri;
    }

    /**
     * @param offsetId
     * @param fileLogId
     * @param distance
     * @param offsetTime
     * @param offsetValue
     * @param offsetValueOri
     */
    public static PmsBusiWaveOffsetDataTemp newInstance(String offsetId,Long fileLogId,
                                                        Integer distance,
                                                        Timestamp offsetTime,
                                                        Integer offsetValue,
                                                        Double offsetValueOri) {
        PmsBusiWaveOffsetDataTemp instance = new PmsBusiWaveOffsetDataTemp(offsetId,fileLogId,distance,offsetTime,offsetValue,offsetValueOri);
        return instance;
    }

    /**
     * 主键=file_log_id-点序号
     */
    private String offsetId;
    /**
     * 波形文件ID
     */
    private Long fileLogId;
    /**
     * 拉出波形文件中的距离值
     */
    private Integer distance;
    /**
     * 拉出值采集数据时刻的时间戳
     */
    private Timestamp offsetTime;
    /**
     * 拉出舍入值（米）-整数
     */
    private Integer offsetValue;
    /**
     * 拉出原始值（米）-小数
     */
    private Double offsetValueOri;

    /**
     * 获取 拉出原始值（米）-小数
     * @return offsetValueOri
     */
    public Double getOffsetValueOri() {
        return offsetValueOri;
    }
    /**
     * 设置 拉出原始值（米）-小数
     * @param offsetValueOri 拉出原始值（米）-小数
     */
    public void setOffsetValueOri(Double offsetValueOri) {
        this.offsetValueOri = offsetValueOri;
    }
    /**
     * 设置 主键=file_log_id-点序号
     */
    public void setOffsetId(String offsetId){
        this.offsetId = offsetId;
    }
    /**
     * 获取 主键=file_log_id-点序号
     */
    public String getOffsetId(){
        return this.offsetId;
    }
    /**
     * 设置 波形文件ID
     */
    public void setFileLogId(Long fileLogId){
        this.fileLogId = fileLogId;
    }
    /**
     * 获取 波形文件ID
     */
    public Long getFileLogId(){
        return this.fileLogId;
    }
    /**
     * 设置 拉出波形文件中的距离值
     */
    public void setDistance(Integer distance){
        this.distance = distance;
    }
    /**
     * 获取 拉出波形文件中的距离值
     */
    public Integer getDistance(){
        return this.distance;
    }
    /**
     * 设置 拉出值采集数据时刻的时间戳
     */
    public void setOffsetTime(Timestamp offsetTime){
        this.offsetTime = offsetTime;
    }
    /**
     * 获取 拉出值采集数据时刻的时间戳
     */
    public Timestamp getOffsetTime(){
        return this.offsetTime;
    }
    /**
     * 设置 拉出舍入值（米）-整数
     */
    public void setOffsetValue(Integer offsetValue){
        this.offsetValue = offsetValue;
    }
    /**
     * 获取 拉出舍入值（米）-整数
     */
    public Integer getOffsetValue(){
        return this.offsetValue;
    }


    @Override
    public Serializable getId() {
        return this.getOffsetId();
    }
}
