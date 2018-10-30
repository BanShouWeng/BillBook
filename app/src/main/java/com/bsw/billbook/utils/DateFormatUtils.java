package com.bsw.billbook.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author leiming
 * @date 2017/10/11
 */
public class DateFormatUtils {
    public static final String COMMON_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String DAY = "dd";
    public static final String YEAR = "yyyy";
    public static final String MONTH = "MM";
    public static final String HOUR = "HH";
    public static final String MINUTE = "mm";
    public static final String SECOND = "ss";

    public static String format(long time, String format) {
        if (time == 0) {
            return "-";
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
            Date date = new Date(time);
            return dateFormat.format(date);
        }
    }

    public static String format(String time, String format) {
        if (TextUtils.isEmpty(time) || time.trim().length() < 2) {
            return "-";
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
            Date date = new Date(Long.parseLong(time));
            return dateFormat.format(date);
        }
    }

    public static long parse(String time, String format) throws ParseException {
        Date date = null;
        SimpleDateFormat formatTiem2long = new SimpleDateFormat(format);
        date = formatTiem2long.parse(time);
        return date.getTime();
    }
}
