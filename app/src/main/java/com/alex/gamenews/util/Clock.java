package com.alex.gamenews.util;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Clock {

    public static String getTime(String date, String formatPattern) {

        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        long difference, minutes, hours;
        long SECOND = 1000, MINUTE = 60 * SECOND, HOUR = 60 * MINUTE, DAY = 24 * HOUR;

        try {
            difference = System.currentTimeMillis() - format.parse(date).getTime();
        } catch (Exception e) {
            difference = 0;
        }

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
        } else return " - " + DateFormat.format("dd.MM.yyyy", new Date(date)).toString();
    }
}
