package io.hustler.enparadignwaether.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class CalenderUtils {
    public static final String DATE_FORMAT_1 = "hh:mm a";
    public static final String DATE_FORMAT_5 = "hh:mm ";
    public static final String DATE_FORMAT_2 = "dd MMM yyyy hh:mm a";
    public static final String DATE_FORMAT_3 = "dd-MMM-yyyy";
    public static final String DATE_FORMAT_4 = "yyyy-MM-dd";
    public static final String DATE_FORMAT_6 = "dd";

    public static String convertDate(long timestamp, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
        SimpleDateFormat dateformat = new SimpleDateFormat(format, Locale.getDefault());
        calendar.setTimeInMillis(timestamp*1000);
        return dateformat.format(calendar.getTime());
    }

    public static final String[] daysArray =

            {
                    "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"
            };

    public static String getDayStringFOWeek(int dayIndex) {
        return daysArray[dayIndex - 1];

    }

    public static String getDayOfWeekFromDate(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp*1000);
        return getDayStringFOWeek(calendar.get(Calendar.DAY_OF_WEEK));
    }
}
