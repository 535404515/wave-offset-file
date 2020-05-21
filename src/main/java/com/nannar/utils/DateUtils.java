package com.nannar.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * @typename : DateUtils
 * @description : (用一段文字描述此类的作用)
 * @author : hangui_zhang
 * @create by : 2018-12-12 15:31:57
 * @version : V1.0
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    protected static Logger logger = LoggerFactory.getLogger(DateUtils.class);
    /**
     * 完整的日期格式(到毫秒):yyyy-MM-dd HH:mm:ss.SSS
     */
    public static String FORMAT_FULLTIME_MINISECOND = "yyyy-MM-dd HH:mm:ss.SSS";
    /**
     * 完整的日期格式(到毫秒):yyyy-MM-dd 00:00:00.000
     */
    public static String FORMAT_FULLTIME_ZEROTIME = "yyyy-MM-dd 00:00:00.000";
    /**
     * 完整的日期格式(到毫秒):yyyy-MM-dd 23:59:59.999
     */
    public static String FORMAT_FULLTIME_LASTTIME = "yyyy-MM-dd 23:59:59.999";
    /**
     * 完整的日期格式(到毫秒):yyyy-MM-dd HH:mm:ss SSS
     */
    public static String FORMAT_FULLTIME_MINISECOND2 = "yyyy-MM-dd HH:mm:ss.SSS";
    /**
     * 完整的日期格式(到毫秒):yyyyMMddHHmmssSSS
     */
    public static String FORMAT_FULLTIME_MINISECOND_SHORT = "yyyyMMddHHmmssSSS";
    /**
     * 完整的日期格式(到秒):yyyy-MM-dd HH:mm:ss
     */
    public static String FORMAT_FULLTIME = "yyyy-MM-dd HH:mm:ss";
    /**
     * 完整的日期格式(到秒):yyyyMMddHHmmss
     */
    public static String FORMAT_FULLTIME_SHORT = "yyyyMMddHHmmss";
    /**
     * 日期格式(年-月-日):yyyy-MM-dd
     */
    public static String FORMAT_DATE = "yyyy-MM-dd";
    /**
     * 日期格式(年-月):yyyy-MM
     */
    public static String FORMAT_MONTH = "yyyy-MM";
    /**
     * 日期格式(年-月-日):yyyy-MM-01
     */
    public static String FORMAT_DATE_MON_FIRST = "yyyy-MM-01";
    /**
     * 日期格式(年月日):yyyyMMdd
     */
    public static String FORMAT_DATE_SHORT = "yyyyMMdd";
    /**
     * 日期格式(时:分:秒):HH:mm:ss
     */
    public static String FORMAT_TIME = "HH:mm:ss";
    /**
     * 日期格式(时:分:秒):HHmmss
     */
    public static String FORMAT_TIME_SHORT = "HHmmss";
    /**
     * 完整日期格式(时:分:秒.毫秒):HH:mm:ss.SSS
     */
    public static String FORMAT_TIMEFULL = "HH:mm:ss.SSS";
    /**
     * 日期格式(时分秒毫秒):HHmmssSSS
     */
    public static String FORMAT_TIMEFULL_SHORT = "HHmmssSSS";
    /**
     * 日期格式yyyyMM
     */
    public static String FORMAT_MONTH_SHORT = "yyyyMM";
    /**
     * 一天当中最早的时间点 " 00:00:00.000"
     */
    public static String FIRST_TIME = " 00:00:00.000";
    /**
     * 一天当中最晚的时间点 " 23:59:59.999"
     */
    public static String LAST_TIME = " 23:59:59.999";

    public static String format(String pattern) {
        return format(pattern,Calendar.getInstance().getTime());
    }

    /**
     * 检测某个字符串是否指定的日期格式
     * 
     * @param dateStr
     * @return
     */
    public static boolean isDate(String dateStr, String datePattern) {
        if(StringUtils.isEmpty(dateStr) || StringUtils.isEmpty(datePattern)) {
            return false;
        }
        //zhanghg 20190813 16:10 修复BUG：日期值为多个日期时仍然返回true。例如：2019-07-01,2019-07-31
        if(dateStr.length() != datePattern.length()) {
            return false;
        }

        try {
            SimpleDateFormat dateFormatter = getDateFormatter(datePattern);
            if(null == dateFormatter) {
                dateFormatter = new SimpleDateFormat(datePattern);
            }
            dateFormatter.parse(dateStr);
        } catch(Exception e) {
            logger.error("ERROR:",e);
            return false;
        }
        return true;
    }

    /**
     * 验证一个字符串格式为：yyyy-MM-dd HH:mm:ss.SSS格式 或 yyyy-MM-dd HH:mm:ss SSS格式。
     * @author        : hangui_zhang
     * @create by     : 2019-02-22 17:47:52
     * @param time
     * @return
     */
    public static boolean isFullTime(String time) {
        if(StringUtils.isEmpty(time)) {
            return false;
        }
        int timeLength = 23;
        if(timeLength != time.length()) {
            return false;
        }
        int timeLen = 19;
        String timeSec = time.substring(0,timeLen);
        int millSplitIndex = 20;
        String millSec = time.substring(millSplitIndex);
        char dotChar = '.';
        char spaceChar = ' ';
        char char21 = time.charAt(timeLen);
        if(dotChar != char21 && spaceChar != char21) {
            return false;
        }
        if(!NumberUtils.isDigits(millSec)) {
            return false;
        }
        boolean isDate = DateUtils.isDate(timeSec,DateUtils.FORMAT_FULLTIME);
        return isDate;
    }

    //zhanghg 20200215 14:34 sampledateformat不非线程安全的，所以使用全局变量有时会出现解析错误。
    //    private static Map<String, SimpleDateFormat> datePattens = new HashMap<String, SimpleDateFormat>();

    public static SimpleDateFormat getDateFormatter(String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat;
    }

    public static String format(String pattern, Date date) {
        return getDateFormatter(pattern).format(date);
    }

    /**
     * 将一个日期转换成yyyyMMdd格式的数字。
     * @author    : hangui_zhang
     * @create by : 2020-04-22 19:42:31
     * @param date
     * @return
     */
    public static Integer formatAsInteger(Date date) {
        String str = getDateFormatter(DateUtils.FORMAT_DATE_SHORT).format(date);
        Integer num = null;
        if(NumberUtils.isCreatable(str)) {
            BigDecimal dec = new BigDecimal(str);
            num = dec.intValue();
        }
        return num;
    }

    public static Long formatAsLong(String pattern, Date date) {
        String str = getDateFormatter(pattern).format(date);
        Long num = null;
        if(NumberUtils.isCreatable(str)) {
            BigDecimal dec = new BigDecimal(str);
            num = dec.longValue();
        }
        return num;
    }

    public static Timestamp nowSqlTimeStamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static java.sql.Date nowSqlDate() {
        return new java.sql.Date(System.currentTimeMillis());
    }

    /**
     * 计算两个日期的最大值
     * @author    : hangui_zhang
     * @create by : 2019-10-18 14:36:46
     * @param date1
     * @param date2
     * @return
     */
    public static Date max(Date date1, Date date2) {
        Date result = null;
        if(null != date1 && null != date2) {
            result = date1;
            if(date1.getTime() < date2.getTime()) {
                result = date2;
            }
        }
        return result;
    }

    /**
     * 计算两个日期的最大值
     * @author    : hangui_zhang
     * @create by : 2019-10-18 14:36:46
     * @param date1
     * @param date2
     * @return
     */
    public static Timestamp max(Timestamp date1, Timestamp date2) {
        Timestamp result = null;
        if(null != date1 && null != date2) {
            result = date1;
            if(date1.getTime() < date2.getTime()) {
                result = date2;
            }
        }
        return result;
    }

    /**
     * 计算两个日期的最大值
     * @author    : hangui_zhang
     * @create by : 2019-10-18 14:36:46
     * @param date1
     * @param date2
     * @return
     */
    public static Date min(Date date1, Date date2) {
        Date result = null;
        if(null != date1 && null != date2) {
            result = date2;
            if(date1.getTime() > date2.getTime()) {
                result = date1;
            }
        }
        return result;
    }

    /**
     * 计算两个日期的最大值
     * @author    : hangui_zhang
     * @create by : 2019-10-18 14:36:46
     * @param date1
     * @param date2
     * @return
     */
    public static Timestamp min(Timestamp date1, Timestamp date2) {
        Timestamp result = null;
        if(null != date1 && null != date2) {
            result = date2;
            if(date1.getTime() < date2.getTime()) {
                result = date1;
            }
        }
        return result;
    }

    /**
     * 以当前电脑时间作参考，根据天数的偏移量计算一个时间对象。
     * 
     * @param days
     *            要偏移的天数。可以为正数也可以为负数。
     * @return
     */
    public static Timestamp nowSqlTimeStampOffset(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR,days);
        return new Timestamp(calendar.getTime().getTime());
    }

    /**
     * 返回当前电脑实时时间
     * 
     * @return 返回当前时间
     */
    public static Date getNowTime() {
        Calendar calNow = Calendar.getInstance();
        Date dtNow = calNow.getTime();
        return dtNow;
    }

    /**
     * @return 返回今天日期，时间部分为0。例如：2006-4-8 00:00:00
     */
    public static Date getToday() {
        return truncate(new Date(),Calendar.DATE);
    }

    /**
     * @return 返回本月第一天，时间部分为0。例如：2006-01-01 00:00:00
     */
    public static Date getCurrentMonthFirstDay() {
        String format = DateUtils.format(DateUtils.FORMAT_DATE_MON_FIRST);
        Date date = DateUtils.toDate(format,DateUtils.FORMAT_FULLTIME_ZEROTIME);
        return date;
    }

    /**
     * @return 返回今天日期，时间部分为23:59:59.999。例如：2006-4-19 23:59:59.999
     */
    public static Date getTodayEnd() {
        return getDayEnd(new Date());
    }

    /**
     * @return 返回今天日期，时间部分为00:00:00.000。例如：2006-4-19 00:00:00.000
     */
    public static Date getTodayFirst() {
        return getDayFirst(new Date());
    }

    /**
     * 将字符串按指定格式转换成日期对象。日期格式必须符合格式规范，并且与字符串匹配，否则发发生转换错误。
     * 
     * @param dateStr
     *            要转换的字符串，例如：2016-01-01
     * @param formatStr
     *            要转换的字符串所对应的日期格式，例如：yyyy-MM-dd
     * @return
     */
    public static Date toDate(String dateStr, String formatStr) {
        Date date1 = null;
        if(!StringUtils.isEmpty(dateStr)) {
            try {
                date1 = getDateFormatter(formatStr).parse(dateStr);
            } catch(Exception e) {
                return null;
            }
        }
        return date1;
    }

    /**
     * 将字符串按指定格式转换成日期对象。日期格式必须符合格式规范，并且与字符串匹配，否则发发生转换错误。
     * 
     * @param dateStr
     *            要转换的字符串，例如：2016-01-01
     * @param formatStr
     *            要转换的字符串所对应的日期格式，例如：yyyy-MM-dd
     * @return
     */
    public static Timestamp toTimestamp(String dateStr, String formatStr) {
        Timestamp date1 = null;
        if(!StringUtils.isEmpty(dateStr)) {
            try {
                Date date2 = getDateFormatter(formatStr).parse(dateStr);
                date1 = new Timestamp(date2.getTime());
            } catch(Exception e) {
                return null;
            }
        }
        return date1;
    }

    /**
     * 将日期对象按指定格式转换成字符串。如果日期为null值，则返回空字符串。日期格式必须符合格式规范，否则发发生转换错误。
     * 
     * @param date
     *            要转换的字符串，例如：{2016-01-01 00:00:00}
     * @param formatStr
     *            要转换的字符串所对应的日期格式，例如：yyyy-MM-dd
     * @return
     */
    public static String toString(Date date, String formatStr) {
        String resultStr = "";
        if(!StringUtils.isEmpty(formatStr)) {
            if(null != date) {
                resultStr = getDateFormatter(formatStr).format(date);
            }
        }
        return resultStr;
    }

    /**
     * 计算某个月的最后一天的日期。
     * 
     * @param year
     *            年 4位年度
     * @param month
     *            月 1~12
     * @return 月底的Date对象。例如：2006-3-31 23:59:59.999
     */
    public static Date getMonthEnd(int year, int month) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.YEAR,year);
        calendar1.set(Calendar.MONTH + 2,month);
        calendar1.set(Calendar.DAY_OF_MONTH,1);
        int day = calendar1.get(Calendar.DAY_OF_YEAR);
        calendar1.set(Calendar.DAY_OF_YEAR,day - 1);
        Date result = calendar1.getTime();
        return result;
    }

    /**
     * 计算某个月的最后一天的日期。
     * 
     * @param when
     *            要计算月底的日期
     * @return 月底的Date对象。例如：2006-3-31 23:59:59.999
     */
    public static Date getMonthEnd(Date when) {
        Assert.notNull(when,"date must not be null !");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(when);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        return getMonthEnd(year,month);
    }

    /**
     * 获取给定日的最后一刻。
     * 
     * @param when
     *            给定日
     * @return 最后一刻。例如：2006-4-19 23:59:59.999
     */
    public static Date getDayEnd(Date when) {
        Date date = truncate(when,Calendar.DATE);
        date = addDays(date,1);
        date.setTime(date.getTime() - 1);
        return date;
    }

    /**
     * 获取给定日的最后一刻。
     * 
     * @param when
     *            给定日
     * @return 最后一刻。例如：2006-4-19 00:00:00.000
     */
    public static Date getDayFirst(Date when) {
        Date date = truncate(when,Calendar.DATE);
        return date;
    }

    /**
     * 获取给定日的第一刻。
     * 
     * @param when
     *            给定日
     * @return 最后一刻。例如：2006-4-19 23:59:59.999
     */
    public static Date getDay(Date when) {
        Date date = truncate(when,Calendar.DATE);
        date = addDays(date,-1);
        date.setTime(date.getTime() + 1);
        return date;
    }

    /**
     * 日期加法
     * 
     * @param when
     *            被计算的日期
     * @param field
     *            the time field. 在Calendar中定义的常数，例如Calendar.DATE
     * @param amount
     *            加数
     * @return 计算后的日期
     */
    public static Date add(Date when, int field, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(when);
        calendar.add(field,amount);
        return calendar.getTime();
    }

    /**
     * 计算给定的日期加上给定的天数。
     * 
     * @param when
     *            给定的日期
     * @param amount
     *            给定的天数
     * @return 计算后的日期
     */
    public static Date addDays(Date when, int amount) {
        return add(when,Calendar.DAY_OF_YEAR,amount);
    }

    /**
     * 计算给定的日期加上给定的月数。
     * 
     * @param when
     *            给定的日期
     * @param amount
     *            给定的月数
     * @return 计算后的日期
     */
    public static Date addMonths(Date when, int amount) {
        return add(when,Calendar.MONTH,amount);
    }

    /**
     * 计算两个时间相差秒数
     * 
     * @param date1
     *            时间1
     * @param date2
     *            时间2
     * @return
     */
    public static String timeMinusSecondStr(Long date1, Long date2) {
        Long edatex1 = date2;
        BigDecimal dec = new BigDecimal((edatex1 - date1) * 1.0 / 1000);
        // 3位小数
        dec = dec.setScale(3,BigDecimal.ROUND_HALF_UP);
        String secs = dec.toString();
        return secs;
    }

    /**
     * 计算两个时间相差秒数
     * 
     * @param date1
     *            时间1
     * @param date2
     *            时间2
     * @return
     */
    public static String timeMinusSecondStr(Date date1, Date date2) {
        return timeMinusSecondStr(date1.getTime(),date2.getTime());
    }

    /**
     * 计算两个时间相差秒数
     * 
     * @param date1
     *            时间1
     * @param date2
     *            时间2
     * @return
     */
    public static Double timeMinusSecond(Long date1, Long date2) {
        Long edatex1 = date2;
        BigDecimal dec = new BigDecimal((edatex1 - date1) * 1.0 / 1000);
        // 3位小数
        dec = dec.setScale(3,BigDecimal.ROUND_HALF_UP);
        return dec.doubleValue();
    }

    /**
     * 计算两个时间相差秒数
     * 
     * @param date1
     *            时间1
     * @param date2
     *            时间2
     * @return
     */
    public static Double timeMinusSecond(Date date1, Date date2) {
        return timeMinusSecond(date1.getTime(),date2.getTime());
    }

    /**
     * 读取文件创建时间
     */
    public static String getCreateTimeStr(String filePath) {
        String strTime = null;
        Process proc = null;
        InputStream is = null;
        BufferedReader br = null;
        InputStreamReader isr = null;
        try {
            proc = Runtime.getRuntime().exec("cmd /C dir " + filePath + "/tc");
            is = proc.getInputStream();
            proc.getOutputStream().close();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String line;
            File file = new File(filePath);
            while((line = br.readLine()) != null) {
                if(line.endsWith(file.getName())) {
                    strTime = line.substring(0,17);
                    break;
                }
            }
        } catch(IOException e) {
            logger.error("ERROR:",e);
        } finally {
            try {
                if(null != br) {
                    br.close();
                }
            } catch(IOException e) {
                logger.error("ERROR1:",e);
            }
            try {
                if(null != isr) {
                    isr.close();
                }
            } catch(IOException e) {
                logger.error("ERROR2:",e);
            }
            try {
                if(null != is) {
                    is.close();
                }
            } catch(IOException e) {
                logger.error("ERROR3:",e);
            }
            if(null != proc) {
                proc.destroy();
            }
        }
        return strTime;
    }

    /**
     * 读取修改时间的方法
     */
    public static String getModifiedTimeStr(String filePath) {
        File f = new File(filePath);
        Calendar cal = Calendar.getInstance();
        long time = f.lastModified();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cal.setTimeInMillis(time);
        return formatter.format(cal.getTime());
    }

    /**
     * 读取修改时间的方法
     */
    public static Date getModifiedTime(String filePath) {
        File f = new File(filePath);
        Calendar cal = Calendar.getInstance();
        long time = f.lastModified();
        cal.setTimeInMillis(time);
        return cal.getTime();
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param normalDate1 日期字符串，格式yyyy-MM-dd
     * @param normalDate2 日期字符串，格式yyyy-MM-dd
     * @return
     */
    public static int differentDaysByMillisecond(String normalDate1, String normalDate2) {
        Date date1 = DateUtils.toDate(normalDate1,DateUtils.FORMAT_DATE);
        Date date2 = DateUtils.toDate(normalDate2,DateUtils.FORMAT_DATE);
        return differentDaysByMillisecond(date1,date2);
    }

    /**
     * 获取计算sdate和edate参数的月份差
     * @param request
     * @return
     */
    public static int getMonthDiff(String sdateStr, String edateStr) {
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        int difDays = DateUtils.differentDaysByMillisecond(sdateStr,edateStr);
        //间隔不大于30天，当作1个月内。超过30天则按公式计算日期月份跨度。
        int monDays = 30;
        int result = 1;
        if(difDays <= monDays) {
            result = 1;
        } else {
            bef.setTime(DateUtils.toDate(sdateStr,DateUtils.FORMAT_DATE));
            aft.setTime(DateUtils.toDate(edateStr,DateUtils.FORMAT_DATE));

            int difMonth = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
            int difMonth2 = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
            result = (Math.abs(difMonth2 + difMonth) + 1);
        }
        return result;
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1, Date date2) {
        int days = (int)((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        return days;
    }

    public static long MINUS_SECOND = 60;
    public static long HOUR_SECOND = 60 * 60;
    public static long DAY_SECOND = 60 * 60 * 24;
    public static long MONTH_SECOND = 60 * 60 * 24 * 30;

    public static String secondDiffBeforeNow(Date date) {
        long time = date.getTime();
        return secondDiffBeforeNow(time);
    }

    public static Long diffSecondBeforeNow(Long preTime) {
        Long edatex1 = System.currentTimeMillis();
        BigDecimal dec = new BigDecimal((edatex1 - preTime) * 1.0 / 1000);
        long longValue = dec.longValue();
        return longValue;
    }

    public static String secondDiffBeforeNow(Long preTime) {
        Long edatex1 = System.currentTimeMillis();
        BigDecimal dec = new BigDecimal((edatex1 - preTime) * 1.0 / 1000);
        long longValue = dec.longValue();
        return getDiffBeforeNowStr(longValue);
    }

    public static String getDiffBeforeNowStr(long longValue) {
        String result = "";
        int five = 5;
        if(longValue < MINUS_SECOND * five) {
            result = "刚刚";
            return result;
        }
        if(longValue < HOUR_SECOND) {
            long minus = longValue / MINUS_SECOND;
            return minus + "分钟前";
        }
        if(longValue < DAY_SECOND) {
            long minus = longValue / HOUR_SECOND;
            return minus + "小时前";
        }
        if(longValue < MONTH_SECOND) {
            long minus = longValue / DAY_SECOND;
            return minus + "天前";
        }
        int yearDay = 365;
        if(longValue < DAY_SECOND * yearDay) {
            long minus = longValue / MONTH_SECOND;
            return minus + "个月前";
        }
        long minus = longValue / DAY_SECOND / yearDay;
        int maxYear = 3;
        if(minus < maxYear) {
            return minus + "年前";
        }
        return "很久以前";
    }

    /**
     * 
     * @author yuhui_cai
     * @param date
     * @return
     */
    public static String strDateCut(String date) {
        return date.replace("-","").replace(":","").replace(" ","").replace(".","").substring(0,16);
    }

}
