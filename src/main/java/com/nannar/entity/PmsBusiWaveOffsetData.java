package com.nannar.entity;

import java.io.Serializable;


/**
 * @author yuhui_cai
 */
public class PmsBusiWaveOffsetData extends BasePojo{

    /**
     *
     */
    private PmsBusiWaveOffsetData() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * @param offsetId
     * @param fileLogId
     * @param offsetValue
     * @param offsetCount
     */
    private PmsBusiWaveOffsetData(String offsetId, Long fileLogId, Integer offsetValue, Integer offsetCount) {
        super();
        this.offsetId = offsetId;
        this.fileLogId = fileLogId;
        this.offsetValue = offsetValue;
        this.offsetCount = offsetCount;
    }
    /**
     *
     * @author    : hangui_zhang
     * @create by : 2019-10-23 11:12:30
     * @param offsetId
     * @param fileLogId
     * @param offsetValue
     * @param offsetCount
     * @return
     */
    public static PmsBusiWaveOffsetData newInstance(String offsetId, Long fileLogId, Integer offsetValue, Integer offsetCount) {
        PmsBusiWaveOffsetData bean = new PmsBusiWaveOffsetData(offsetId,fileLogId,offsetValue,offsetCount);
        return bean;
    }


    /**
     * 主键=file_log_id-拉出位置
     */
    private String offsetId;
    /**
     * 波形文件ID
     */
    private Long fileLogId;
    /**
     * 拉出舍入值（米）-整数
     */
    private Integer offsetValue;
    /**
     * 拉出位置重合次数
     */
    private Integer offsetCount;


    /**
     * 设置 主键=file_log_id-拉出位置
     */
    public void setOffsetId(String offsetId){
        this.offsetId = offsetId;
    }
    /**
     * 获取 主键=file_log_id-拉出位置
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
    /**
     * 设置 拉出位置重合次数
     */
    public void setOffsetCount(Integer offsetCount){
        this.offsetCount = offsetCount;
    }
    /**
     * 获取 拉出位置重合次数
     */
    public Integer getOffsetCount(){
        return this.offsetCount;
    }


    @Override
    public Serializable getId() {
        // TODO Auto-generated method stub
        return this.getOffsetId();
    }
}