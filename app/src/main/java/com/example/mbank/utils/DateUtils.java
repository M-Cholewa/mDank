package com.example.mbank.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private static SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM", new Locale("PL", "pl"));

    public static String getPastDate(int daysBack) {
        return formatter .format(new Date(System.currentTimeMillis() - daysBack * 24 * 60 * 60 * 1000));
    }

    public static String getFormatedPastDate(int daysBack, String pattern) {
        formatter.applyPattern(pattern);
        return formatter .format(new Date(System.currentTimeMillis() - daysBack * 24 * 60 * 60 * 1000));
    }
}
