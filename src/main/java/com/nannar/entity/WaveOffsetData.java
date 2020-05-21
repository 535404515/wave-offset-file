package com.nannar.entity;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nannar.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author    : hangui_zhang
 * @create by : 2019-10-18 14:10:55
 */
public class WaveOffsetData {


    /**
     * 获取 距离值
     * @return distance
     */
    public Double getDistance() {
        return distance;
    }
    /**
     * 设置 距离值
     * @param distance 距离值
     */
    public void setDistance(Double distance) {
        this.distance = distance;
    }

    /**
     * 忽略值
     */
    private static double IGNORE_NUM = 99999.0;
    /**
     * 将距离值取整
     * @author    : hangui_zhang
     * @create by : 2019-10-18 14:23:05
     * @return
     */
    public Integer getDistanceInt() {
        Integer result = null;
        if(null != distance && distance < WaveOffsetData.IGNORE_NUM) {
            BigDecimal dec = new BigDecimal(distance);
            result = dec.intValue();
        }
        return result;
    }


    /**
     * 获取 拉出值
     * @return offset
     */
    public List<Double> getOffset() {
        return offset;
    }
    /**
     * 设置 拉出值
     * @param offset 拉出值
     */
    public void setOffset(List<Double> offset) {
        this.offset = offset;
    }
    /**
     * 获取第1个位置的拉出值。不存在或99999.0返回null
     * @author    : hangui_zhang
     * @create by : 2019-10-18 15:02:46
     * @return
     */
    public Double getOffset1() {
        int index = 0;
        if(null != offset && offset.size() > index) {
            return offset.get(index);
        }
        return null;
    }
    /**
     * 获取第2个位置的拉出值。不存在或99999.0返回null
     * @author    : hangui_zhang
     * @create by : 2019-10-18 15:03:24
     * @return
     */
    public Double getOffset2() {
        int index = 1;
        if(null != offset && offset.size() > index) {
            return offset.get(index);
        }
        return null;
    }

    /**
     * 获取第3个位置的拉出值。不存在或99999.0返回null
     * @author    : hangui_zhang
     * @create by : 2019-10-18 15:03:32
     * @return
     */
    public Double getOffset3() {
        int index = 2;
        if(null != offset && offset.size() > index) {
            return offset.get(index);
        }
        return null;
    }

    /**
     * 获取第4个位置的拉出值。不存在或99999.0返回null
     * @author    : hangui_zhang
     * @create by : 2019-10-18 15:03:37
     * @return
     */
    public Double getOffset4() {
        int index = 3;
        if(null != offset && offset.size() > index) {
            return offset.get(index);
        }
        return null;
    }

    /**
     * 获取第1个位置的拉出值。不存在或99999.0返回null
     * @author    : hangui_zhang
     * @create by : 2019-10-18 15:02:46
     * @return
     */
    public Integer getOffset1Int() {
        Integer result = null;
        Double offsetValue = getOffset1();
        if(null != offsetValue) {
            result = offsetValue.intValue();
        }
        return result;
    }
    /**
     * 获取第2个位置的拉出值。不存在或99999.0返回null
     * @author    : hangui_zhang
     * @create by : 2019-10-18 15:03:24
     * @return
     */
    public Integer getOffset2Int() {
        Integer result = null;
        Double offsetValue = getOffset2();
        if(null != offsetValue) {
            result = offsetValue.intValue();
        }
        return result;
    }

    /**
     * 获取第3个位置的拉出值。不存在或99999.0返回null
     * @author    : hangui_zhang
     * @create by : 2019-10-18 15:03:32
     * @return
     */
    public Integer getOffset3Int() {
        Integer result = null;
        Double offsetValue = getOffset3();
        if(null != offsetValue) {
            result = offsetValue.intValue();
        }
        return result;
    }

    /**
     * 获取第4个位置的拉出值。不存在或99999.0返回null
     * @author    : hangui_zhang
     * @create by : 2019-10-18 15:03:37
     * @return
     */
    public Integer getOffset4Int() {
        Integer result = null;
        Double offsetValue = getOffset4();
        if(null != offsetValue) {
            result = offsetValue.intValue();
        }
        return result;
    }

    /**
     * 获取 时间戳
     * @return time
     */
    public String getTime() {
        return time;
    }

    /**
     * 获取时间戳
     * @author    : hangui_zhang
     * @create by : 2019-10-18 14:26:48
     * @param dateStr 日期，格式：yyyy-MM-dd
     * @return
     */
    public Timestamp getOffsetTime(String dateStr) {
        Timestamp result = null;
        if(!StringUtils.isEmpty(dateStr) && !StringUtils.isEmpty(time)) {
            String str = dateStr + " " + time;
            Date date = DateUtils.toDate(str,DateUtils.FORMAT_FULLTIME_MINISECOND);
            result = new Timestamp(date.getTime());
        }
        return result;
    }

    /**
     * 获取时间戳
     * @author    : hangui_zhang
     * @create by : 2019-10-18 14:26:48
     * @param date 日期，格式：yyyy-MM-dd
     * @return
     */
    public Timestamp getOffsetTime(Date date) {
        Timestamp result = null;
        if(null != date) {
            String dateStr = DateUtils.toString(date,DateUtils.FORMAT_DATE);
            result = getOffsetTime(dateStr);
        }
        return result;
    }
    /**
     * 设置 时间戳
     * @param time 时间戳
     */
    public void setTime(String time) {
        this.time = time;
    }
    /**
     * 距离值
     */
    private Double distance;
    /**
     * 拉出值
     */
    private List<Double> offset = new ArrayList<Double>();
    /**
     * 时间戳
     */
    private String time;

    public static WaveOffsetData newInstance(Double distance,List<Double> offset,String time) {
        WaveOffsetData data = new WaveOffsetData();
        data.setDistance(distance);
        data.setOffset(offset);
        data.setTime(time);
        return data;
    }

    /**
     *
     */
    private WaveOffsetData() {

    }

}
