package vn.ltt.shop.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class InstantUtils {
    private static final String PATTERN_FORMAT = "HH:mm dd-MM-yyyy";

    private static final String DATE_PATTERN_FORMAT = "dd-MM-yyyy";

    public static String instantToString(Instant instant) {
        return instantToString(instant, null);
    }

    public static String instantToString(Instant instant, String patternFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(patternFormat != null ? patternFormat : PATTERN_FORMAT).withZone(ZoneId.systemDefault());
        return formatter.format(instant);
    }

    public static String instantToStringDate(Instant instant) {
        return instantToStringDate(instant, null);
    }

    public static String instantToStringDate(Instant instant, String patternFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(patternFormat != null ? patternFormat : DATE_PATTERN_FORMAT).withZone(ZoneId.systemDefault());
        return formatter.format(instant);
    }
}
