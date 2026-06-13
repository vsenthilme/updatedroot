package com.spark.demo.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
    public static Date[] addTimeToDatesForSearch(Date startDate, Date endDate) {
        Calendar startCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        startCalendar.setTime(startDate);

        Calendar endCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        endCalendar.setTime(endDate);

        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);

        endCalendar.set(Calendar.HOUR_OF_DAY, 23);
        endCalendar.set(Calendar.MINUTE, 59);
        endCalendar.set(Calendar.SECOND, 59);
        endCalendar.set(Calendar.MILLISECOND, 999);

        return new Date[]{startCalendar.getTime(), endCalendar.getTime()};
    }
}