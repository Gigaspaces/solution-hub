package org.springframework.data.xap.examples.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Leonid_Poliakov
 */
public class Output {
    public static String dateTime() {
        return new SimpleDateFormat("hh:mm:ss dd-MM-yyyy").format(new Date());
    }
}