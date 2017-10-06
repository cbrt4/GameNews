package com.alex.gamenews.util;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Clock {

    public static String getTime(String date, String formatPattern) {

        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        long dateMillis, difference, minutes, hours;
        long SECOND = 1000, MINUTE = 60 * SECOND, HOUR = 60 * MINUTE, DAY = 24 * HOUR;

        try {
            dateMillis = format.parse(date).getTime();
        } catch (Exception e) {
            dateMillis = System.currentTimeMillis();
        }

        difference = System.currentTimeMillis() - dateMillis;
        if (difference < SECOND) return " - Just now";
        else if (difference >= SECOND && difference < MINUTE) return " - Last minute";
        else if (difference >= MINUTE && difference < HOUR) {
            minutes = difference / MINUTE;
            return " - " + (minutes == 1 ? " a minute ago" : +minutes + " minutes ago");
        } else if (difference >= HOUR && difference < DAY) {
            hours = difference / HOUR;
            return " - " + (hours == 1 ? " an hour ago" : + hours + " hours ago");
        } else if (difference >= DAY && difference < 2 * DAY) {
            return " - Yesterday";
        } else return " - " + DateFormat.format("d MMM yyyy 'at' HH:mm", new Date(dateMillis)).toString();
    }
}
