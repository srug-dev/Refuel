package com.srug.mobile.refuel.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataFormatOperations {

    public static final String DATE_FORMAT = "dd-MM-yyyy";

    public static String parseDateFormat(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }
}
