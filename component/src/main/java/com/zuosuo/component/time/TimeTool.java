package com.zuosuo.component.time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeTool {

    public static final Date getOffsetDate(Date date, DiscTime discTime) {
        Calendar calendar = getCalendarByDate(date);
        if (discTime.getYear() != 0){
            calendar.add(Calendar.YEAR, discTime.getYear());
        }
        if (discTime.getMonth() != 0){
            calendar.add(Calendar.MONTH, discTime.getMonth());
        }
        if (discTime.getDay() != 0){
            calendar.add(Calendar.DATE, discTime.getDay());
        }
        if (discTime.getHour() != 0){
            calendar.add(Calendar.HOUR, discTime.getHour());
        }
        if (discTime.getMinute() != 0){
            calendar.add(Calendar.MINUTE, discTime.getMinute());
        }
        if (discTime.getSecond() != 0){
            calendar.add(Calendar.SECOND, discTime.getSecond());
        }
        return calendar.getTime();
    }

    public static final Calendar getCalendarByDate(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar;
    }

    public static final long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    public static final String formatDate(Date date) {
        return formatDate(date, TimeFormat.DEFAULT_DATETIME.getValue());
    }

    public static final String formatDate(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final String friendlyTime(Date date) {
        return friendlyTime(date, null);
    }

    public static final String friendlyTime(Date date, String format) {
        if (date == null) {
            return "";
        }
        Date now = new Date();
        long discTime = (now.getTime() - date.getTime()) / 1000;
        if (discTime < 0) {
            return "穿越了!";
        }
        long discMinutes = Long.parseLong(String.valueOf(discTime / 60));
        long discHours = Long.parseLong(String.valueOf(discTime / 3600));
        long discDays = Long.parseLong(String.valueOf(discTime / 86400));
        if (discTime <= 30) {
            return "刚刚";
        } else if(discMinutes < 60) {
            return discMinutes + "分钟前";
        } else if(discDays == 0) {
            return discHours + "小时前";
        } else if(discDays < 7) {
            return discDays + "天前";
        }
        if (format != null && !format.isEmpty()) {
            return formatDate(date, format);
        }
        return formatDate(date);
    }
}
