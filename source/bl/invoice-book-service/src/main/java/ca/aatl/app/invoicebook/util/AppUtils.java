/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Nov-01  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author GShokar
 */
public final class AppUtils {

    public final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public final static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private static Date minimumDate = null;

    public static Date getMinimumDate() {

        if (minimumDate == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(1900, 0, 1, 0, 0, 0);
            minimumDate = calendar.getTime();
        }

        return minimumDate;
    }

    public static String getGUID() {
        return UUID.randomUUID().toString().toUpperCase();
    }

    public static Date currentDate() {

        return java.sql.Date.valueOf(LocalDate.now());
    }

    public static Date parseDate(String dateValue) {
        Date date = null;

        if (!isNullOrEmpty(dateValue)) {
            try {
                date = AppUtils.dateFormat.parse(dateValue);
            } catch (ParseException ex) {

            }
        }

        return date;
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }

    public static String dateToString(Date date) {
        String value = null;

        if (date != null) {
            value = dateFormat.format(date);
        }

        return value;
    }

    public static String timeToString(Date date) {
        String value = null;

        if (date != null) {
            value = timeFormat.format(date);
        }

        return value;
    }

    public static String formatPhoneNumber(String phoneNumber) {
        String formattedPhoneNumber = phoneNumber;

        if (!isNullOrEmpty(phoneNumber) && phoneNumber.length() >= 10) {

            StringBuilder sb = new StringBuilder();
            int i = 0;

            if (phoneNumber.substring(i, i + 1).equals("1")) {
                sb.append("1-");
                i++;
            }

            sb.append(phoneNumber.substring(i, i + 3));
            sb.append("-");
            i = i + 3;
            sb.append(phoneNumber.substring(i, i + 3));
            sb.append("-");
            i = i + 3;
            sb.append(phoneNumber.substring(i));

            formattedPhoneNumber = sb.toString();

        }
        return formattedPhoneNumber;
    }
}
