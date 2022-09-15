package vn.ltt.shop.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class InstantUtils {
    private static final String PATTERN_FORMAT = "HH:mm dd-MM-yyyy";

    private static final String DAY_PATTERN_FORMAT = "dd-MM-yyyy";

    private static final String MONTH_PATTERN_FORMAT = "MM-yyyy";
    private static final String YEAR_PATTERN_FORMAT = "yyyy";


    public static String instantToString(Instant instant) {
        return instantToString(instant, null);
    }

    public static String instantToString(Instant instant, String patternFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(patternFormat != null ? patternFormat : PATTERN_FORMAT).withZone(ZoneId.systemDefault());
        return formatter.format(instant);
    }

    public static String instantToStringDay(Instant instant) {
        return instantToStringDay(instant, null);
    }

    public static String instantToStringDay(Instant instant, String patternFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(patternFormat != null ? patternFormat : DAY_PATTERN_FORMAT).withZone(ZoneId.systemDefault());
        return formatter.format(instant);
    }

    public static String instantToStringMonth(Instant instant) {
        return instantToStringMonth(instant, null);
    }

    public static String instantToStringMonth(Instant instant, String patternFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(patternFormat != null ? patternFormat : MONTH_PATTERN_FORMAT).withZone(ZoneId.systemDefault());
        return formatter.format(instant);
    }

    public static String instantToStringYear(Instant instant) {
        return instantToStringYear(instant, null);
    }

    public static String instantToStringYear(Instant instant, String patternFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(patternFormat != null ? patternFormat : YEAR_PATTERN_FORMAT).withZone(ZoneId.systemDefault());
        return formatter.format(instant);
    }
}
