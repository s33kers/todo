package us.martink.bl.utils;

import java.time.format.DateTimeFormatter;

/**
 * Created by tadas.
 */
public final class CoreUtils {
    private CoreUtils() {
    }

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final DateTimeFormatter FORMATTER_DATE = DateTimeFormatter.ofPattern(DATE_FORMAT);

}
