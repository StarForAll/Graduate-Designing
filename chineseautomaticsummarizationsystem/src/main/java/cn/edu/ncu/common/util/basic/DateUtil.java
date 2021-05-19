package cn.edu.ncu.common.util.basic;


import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: XiongZhiCong
 * @Description: 日期操作工具类
 * @Date: Created in 15:13 2021/4/8
 * @Modified By:
 */
public class DateUtil {
    private static String[] parsePatterns = { "yyyy-MM-dd",
            "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyy/MM/dd",
            "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd",
            "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"
    };
    public static final SimpleDateFormat ymdhms = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String formatYMDHMS(Date date) {
        return ymdhms.format(date);
    }


    /**
     * 日期型字符串转化为日期 格式 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy.MM.dd",
     * "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     */
    public static Date parseDate(String str) {
        if (str == null) {
            return null;
        }
        try {
            return DateUtils.parseDate(
                    str, parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 得到当前日期
     */
    public static Date getCurrentDate() {
        return new Date();
    }


    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getCurrentDateString() {
        return getCurrentDateString("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 pattern用于指定格式
     */
    public static String getCurrentDateString(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到日期时间字符串
     */
    public static String formatDateTime(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * 判断两个日期时间是否是同一天
     */
    public static boolean isSameDay(final Date date1, final Date date2){
        return DateUtils.isSameDay(date1, date2);
    }

    /**
     * 判断两个日期的大小,date1大于date2，返回1,date1小于date2，返回-1，date1等于date2，返回0
     */
    public static int compareDate(Date date1, Date date2){
        if (date1.getTime() == date2.getTime()) {
            return 0;
        }
        else if (date1.getTime() > date2.getTime()){
            return 1;
        }
        else {
            return -1;
        }
    }

    /**
     * 获取指定日期前后arg1天
     * arg0 : 指定日期 Date类型
     * arg1 : int型,正数向后天数,0当天,负数向前天数
     */
    public static Date addDays(Date date, int day){
        return DateUtils.addDays(date, day);
    }

    /**
     * 获取指定日期前后arg1小时
     * arg0 : 指定日期 Date类型
     * arg1 : int型,正数向后天数,0当天,负数向前天数
     */
    public static Date addHours(Date date, int hour){
        return DateUtils.addHours(date, hour);
    }

    /**
     * 获取指定日期前后arg1分钟
     * arg0 : 指定日期 Date类型
     * arg1 : int型,正数向后天数,0当天,负数向前天数
     */
    public static Date addMinutes(Date date, int minute){
        return DateUtils.addHours(date, minute);
    }

    /**
     * 获取指定日期前后arg1秒
     * arg0 : 指定日期 Date类型
     * arg1 : int型,正数向后天数,0当天,负数向前天数
     */
    public static Date addSeconds(Date date, int second){
        return DateUtils.addHours(date, second);
    }

}
