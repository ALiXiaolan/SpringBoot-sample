package com.sample.util;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

/**
 * 此类为日期帮助类(专属于某个时区)
 * 方法都参见
 *
 * @author admin
 * @version 1.0
 * @see DateUtil
 * <p>
 * 所有的操作方法都基于某个时区
 */
public class DateZoneUtil {
    public static final ZoneOffset ZONE_OFFSET = ZoneOffset.of("+8");

    public static final DateTimeFormatter DATE_TIME_FORMATTER_DAY = DateTimeFormatter.ofPattern(DateUtil.DATE_FORMAT_DAY).withZone(ZONE_OFFSET);
    public static final DateTimeFormatter DATE_TIME_FORMATTER_SECOND = DateTimeFormatter.ofPattern(DateUtil.DATE_FORMAT_SECOND).withZone(ZONE_OFFSET);
    public static final DateTimeFormatter DATE_TIME_FORMATTER_MILLISECOND = DateTimeFormatter.ofPattern(DateUtil.DATE_FORMAT_MILLISECOND).withZone(ZONE_OFFSET);

    /**
     * 根据dateStr长度转换成不同的时间
     * {@link DateUtil#DATE_FORMAT_DAY} 长度8
     * {@link DateUtil#DATE_FORMAT_SECOND} 长度14
     *
     * @param dateStr
     * @return
     */
    public static Date stringToDate(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        int len = dateStr.length();
        if (len == 8) {
            DateZoneUtil.stringToDateDay(dateStr);
        } else if (len == 14) {
            return DateZoneUtil.stringToDateSecond(dateStr);
        } else if (len == 17) {
            return DateZoneUtil.stringToDateMillisecond(dateStr);
        }
        return null;
    }

    /**
     * @param dateStr
     * @return
     */
    public static Date stringToDateDay(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        return Date.from(LocalDate.parse(dateStr, DATE_TIME_FORMATTER_DAY).atTime(LocalTime.MIN).toInstant(ZONE_OFFSET));
    }

    /**
     * @param dateStr
     * @return
     */
    public static Date stringToDateSecond(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        return Date.from(LocalDateTime.parse(dateStr, DATE_TIME_FORMATTER_SECOND).toInstant(ZONE_OFFSET));
    }

    /**
     * @param dateStr
     * @return
     */
    public static Date stringToDateMillisecond(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        return Date.from(LocalDateTime.parse(dateStr, DATE_TIME_FORMATTER_MILLISECOND).toInstant(ZONE_OFFSET));
    }

    /**
     * @param date
     * @return
     */
    public static String dateToStringDay(Date date) {
        if (date == null) {
            return null;
        }
        return DATE_TIME_FORMATTER_DAY.format(date.toInstant());
    }

    /**
     * @param date
     * @return
     */
    public static String dateToStringSecond(Date date) {
        if (date == null) {
            return null;
        }
        return DATE_TIME_FORMATTER_SECOND.format(date.toInstant());
    }

    /**
     * @param date
     * @return
     */
    public static String dateToStringMillisecond(Date date) {
        if (date == null) {
            return null;
        }
        return DATE_TIME_FORMATTER_MILLISECOND.format(date.toInstant());
    }


    /**
     * @param date
     * @param unit
     * @return
     * @see DateUtil#getFloorDate(Date, ChronoUnit, ZoneOffset)
     */
    public static Date getFloorDate(Date date, ChronoUnit unit) {
        return DateUtil.getFloorDate(date, unit, ZONE_OFFSET);
    }

    /**
     * @param date
     * @param unit
     * @return
     * @see DateUtil#getCeilDate(Date, ChronoUnit, ZoneOffset)
     */
    public static Date getCeilDate(Date date, ChronoUnit unit) {
        return DateUtil.getCeilDate(date, unit, ZONE_OFFSET);
    }

    /**
     * @param startDate
     * @param endDate
     * @param unit
     * @return
     * @see DateUtil#rangeDate(Date, Date, ChronoUnit, ZoneOffset)
     */
    public static List<Date[]> rangeDate(Date startDate, Date endDate, ChronoUnit unit) {
        return DateUtil.rangeDate(startDate, endDate, unit, ZONE_OFFSET);
    }

    /**
     * @param startDate
     * @param endDate
     * @see DateUtil#formatDateParam(Date, Date, ZoneOffset)
     */
    public static void formatDateParam(Date startDate, Date endDate) {
        DateUtil.formatDateParam(startDate, endDate, ZONE_OFFSET);
    }
}
