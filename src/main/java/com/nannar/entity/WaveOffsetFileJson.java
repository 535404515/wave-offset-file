package com.nannar.entity;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author    : hangui_zhang
 * @create by : 2019-10-18 14:10:55
 */
public class WaveOffsetFileJson {

    /**
     * 获取 下一站ID
     * @return nextStation
     */
    public Integer getNextStation() {
        return nextStation;
    }
    /**
     * 设置 下一站ID
     * @param nextStation 下一站ID
     */
    public void setNextStation(Integer nextStation) {
        this.nextStation = nextStation;
    }
    /**
     * 获取 采样点数量
     * @return samples
     */
    public Integer getSamples() {
        return samples;
    }
    /**
     * 设置 采样点数量
     * @param samples 采样点数量
     */
    public void setSamples(Integer samples) {
        this.samples = samples;
    }
    /**
     * 获取 创建时间
     * @return createTime
     */
    public Timestamp getCreateTime() {
        return createTime;
    }
    /**
     * 设置 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
    /**
     * 获取 当前站ID
     * @return currentStation
     */
    public Integer getCurrentStation() {
        return currentStation;
    }
    /**
     * 设置 当前站ID
     * @param currentStation 当前站ID
     */
    public void setCurrentStation(Integer currentStation) {
        this.currentStation = currentStation;
    }
    /**
     * 获取 拉出值数据清单
     * @return data
     */
    public List<WaveOffsetData> getData() {
        return data;
    }
    /**
     * 设置 拉出值数据清单
     * @param data 拉出值数据清单
     */
    public void setData(List<WaveOffsetData> data) {
        this.data = data;
    }
    /**
     * 下一站ID
     */
    private Integer nextStation;
    /**
     * 采样点数量
     */
    private Integer samples;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 当前站ID
     */
    private Integer currentStation;
    /**
     * 拉出值数据清单
     */
    private List<WaveOffsetData> data = new ArrayList<WaveOffsetData>();
    /**
     *
     */
    public WaveOffsetFileJson() {

    }

}

