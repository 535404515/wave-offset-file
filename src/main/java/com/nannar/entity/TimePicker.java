package com.nannar.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Caiyuhui
 * @date 2020/5/15
 * @time 9:08
 */
public class TimePicker {
    /**
     * 起始时间毫秒数
     */
    private Long startTime;
    /**
     * 上次拾取时间毫秒数
     */
    private Long preTime;

    /**
     *
     */
    private TimePicker() {
        // TODO Auto-generated constructor stub
    }

    public static TimePicker newInstance() {
        TimePicker util = new TimePicker();
        util.startTime = System.currentTimeMillis();
        util.preTime = System.currentTimeMillis();
        return util;
    }

    /**
     * 获取距离上一个时间节点的秒数
     *
     * @param autoPick
     *            是否自动标记为下一个起点
     * @return
     */
    public String stepSecond(boolean autoPick) {
        Long edatex1 = System.currentTimeMillis();
        BigDecimal dec = new BigDecimal((edatex1 - preTime) * 1.0 / 1000);
        // 3位小数
        dec = dec.setScale(3, BigDecimal.ROUND_HALF_UP);
        String secs = dec.toString();
        if (autoPick) {
            preTime = edatex1;
        }
        return secs;
    }

    public void pick() {
        preTime = System.currentTimeMillis();
    }

    /**
     * 计算从开始到当前的总时间
     *
     * @return
     */
    public String allSecond() {
        Long edatex1 = System.currentTimeMillis();
        BigDecimal dec = new BigDecimal((edatex1 - startTime) * 1.0 / 1000);
        // 3位小数
        dec = dec.setScale(3, BigDecimal.ROUND_HALF_UP);
        String secs = dec.toString();
        return secs;
    }

    /**
     * 获取 起始时间毫秒数
     *
     * @return startTime
     */
    public Long getStartTime() {
        return startTime;
    }

    /**
     * 设置 起始时间毫秒数
     *
     * @param startTime
     *            起始时间毫秒数
     */
    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取 上次拾取时间毫秒数
     *
     * @return preTime
     */
    public Long getPreTime() {
        return preTime;
    }

    /**
     * 设置 上次拾取时间毫秒数
     *
     * @param preTime
     *            上次拾取时间毫秒数
     */
    public void setPreTime(Long preTime) {
        this.preTime = preTime;
    }

    /**
     * 获取起始时间
     *
     * @return
     */
    public Date getStart() {
        return new Date(this.startTime);
    }

    /**
     * 获取终止时间
     *
     * @return
     */
    public Date getEnd() {
        return new Date(this.preTime);
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public Date getNowTime() {
        return new Date(System.currentTimeMillis());
    }
}
