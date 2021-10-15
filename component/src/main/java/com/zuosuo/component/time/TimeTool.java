package com.zuosuo.component.time;

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
}
