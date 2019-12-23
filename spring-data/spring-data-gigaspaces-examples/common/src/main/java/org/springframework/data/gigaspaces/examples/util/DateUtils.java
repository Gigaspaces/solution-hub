package org.springframework.data.gigaspaces.examples.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Anna_Babich.
 */
public class DateUtils {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("hh dd-MM-yyyy");

    public static Date getDate(String date) {
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String getFormatDate(Date date) {
        return sdf.format(date);
    }
}