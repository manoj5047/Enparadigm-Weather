package io.hustler.enparadignwaether.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalenderUtils {
    public static final String DATE_FORMAT_1 = "hh:mm a";
    public static final String DATE_FORMAT_2 = "dd MMM yyyy hh:mm a";
    public static final String DATE_FORMAT_3 = "dd-MMM-yyyy";
    public static final String DATE_FORMAT_4 = "yyyy-MM-dd";

    public static String convertDate(long timestamp, String format) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat(format, Locale.getDefault());
        calendar.setTimeInMillis(timestamp);
        return dateformat.format(calendar.getTime());
    }
}
