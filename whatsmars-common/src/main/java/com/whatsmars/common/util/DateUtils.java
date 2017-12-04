package com.whatsmars.common.util;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {

    /**
     * 日期格式 yyyy-MM-dd
     */
    public final static String DATEFORMAT = "yyyy-MM-dd";
    /**
     * 日期格式 yyyyMMdd
     */
    public final static String DATEFORMAT2 = "yyyyMMdd";
    /**
     * 日期格式 yyyy/MM/dd
     */
    public final static String DATEFORMAT3 = "yyyy/MM/dd";
    /**
     * 时间格式 HH:mm:ss
     */
    public final static String TIMEFORMAT = "HH:mm:ss";
    /**
     * 日期时间格式 yyyy-MM-dd HH:mm:ss
     */
    public final static String DATETIMEFORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyy-MM
     */
    public final static String DATE_SHORT_FORMAT = "yyyy-MM";
    /**
     * yyyyMM
     */
    public final static String DATE_SHORT_FORMAT2 = "yyyyMM";

    public static String DATETIME_FORMAT_REGEX = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";//yyyy-MM-dd HH:mm:ss
    public static String DATE_FORMAT_REGEX = "\\d{4}-\\d{2}-\\d{2}";//yyyy-MM-dd
    public static String SHORTDATE_FORMAT_REGEX = "\\d{4}-\\d{2}";//yyyy-MM
    public static String DATE_FORMAT2_REGEX = "\\d{4}\\d{2}\\d{2}";//yyyyMMdd
    public static String SHORTDATE_FORMAT2_REGEX = "\\d{4}\\d{2}";//yyyyMM

    /**
     * 获取时间日期格式化dateformat
     *
     * @param pattern 格式
     * @return
     */
    public static DateFormat getFormat(String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            return null;
        }
        return new SimpleDateFormat(pattern);
    }

    /**
     * 将string类型date格式化为date类型
     *
     * @param dateString string类型date
     * @param pattern    格式
     * @return
     */
    public static Date getDateFormat(String dateString, String pattern) {
        if (StringUtils.isEmpty(dateString)) {
            return null;
        }
        try {
            return getFormat(pattern).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getStringFormat(Date date, String pattern) {
        if (null == date) {
            return null;
        }
        return getFormat(pattern).format(date);
    }

    /**
     * 将string类型date重新格式化为string类型
     *
     * @param dateString
     * @param srcPattern
     * @param targetPattern
     * @return
     */
    public static String getStringReformat(String dateString, String srcPattern, String targetPattern) {
        Date date = getDateFormat(dateString, srcPattern);
        return getStringFormat(date, targetPattern);
    }

    /**
     * 获取当前时间的前一天
     *
     * @return
     */
    public static Date getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取本月的第一天
     *
     * @return
     */
    public static Date getFirstDayByMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取上一个月的第一天
     *
     * @return
     */
    public static Date getFirstDayByLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 根据月份获取当月第一天
     *
     * @param month
     * @return
     */
    public static Date getFirstDayByMonth(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取某个月份的第一天
     *
     * @param month
     * @return
     */
    public static Date getFirstDayByMonth(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 计算两个日期之间相隔的月份
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<String> intervalMonths(Date startDate, Date endDate) {
        List<String> monthList = new ArrayList<String>();

        Calendar tmp = Calendar.getInstance();
        tmp.setTime(startDate);
        tmp.set(Calendar.DAY_OF_MONTH, tmp.getActualMaximum(Calendar.DAY_OF_MONTH) - 1);

        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        start.set(Calendar.DAY_OF_MONTH, 1);

        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        end.set(Calendar.DAY_OF_MONTH, end.getActualMaximum(Calendar.DAY_OF_MONTH));

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_SHORT_FORMAT);
        for (; tmp.after(start) && tmp.before(end); tmp.add(Calendar.MONTH, 1), tmp.set(Calendar.DAY_OF_MONTH, tmp.getActualMaximum(Calendar.DAY_OF_MONTH) - 1)) {
            monthList.add(sdf.format(tmp.getTime()));
        }
        return monthList;
    }

    /**
     * 传入时间和当前时间比较最大获取上个月，返回虽然Date，实际时间是yyyyMM01 00:00:00.0
     *
     * @param date
     * @return
     */
    public static Date maxLastMonth(Date date) {
        Date thisMonth = DateUtils.getDateFormat(DateUtils.getStringFormat(new Date(), DateUtils.DATE_SHORT_FORMAT), DateUtils.DATE_SHORT_FORMAT);
        if (thisMonth.compareTo(date) <= 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(thisMonth);
            calendar.add(Calendar.MONTH, -1);
            return calendar.getTime();
        }
        return date;
    }

    public static Date getMonth(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + month);
        return calendar.getTime();
    }

    public static Date getFirstDayByMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static int betweenDays(Date beforeDate, Date afterDate) {
        if (null == beforeDate || null == afterDate) {
            throw new NullPointerException("date can't be null");
        }
        if (beforeDate.after(afterDate)) {
            return 0;
        }
        return (int) ((afterDate.getTime() - beforeDate.getTime()) / (1000 * 3600 * 24));
    }
}
