package com.profile.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
    public static Date parse(String s, String pattern) throws ParseException {
        return new SimpleDateFormat(pattern).parse(s);
    }

    public static String format(Long time, String pattern) {
        return format(new Date(time), pattern);
    }

    public static String format(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static String getTimeInterval(long start, long end) {
        if (start > end) {
            return "start bigger than end";
        }

        final long interval = end - start;

        if (interval == 0L) {
            return "0ms";
        }

        final long milliseconds = interval % 1000;
        final long seconds = (interval / 1000) % 60;
        final long minutes = (interval / 1000 / 60) % 60;
        final long hours = interval / 1000 / 60 / 60;

        return "Time interval: " + hours + " hours(s) " + minutes + " minute(s) "
                + seconds + " second(s) " + milliseconds + " millisecond(s)";
    }
}
