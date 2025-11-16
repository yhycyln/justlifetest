package com.example.justlifetest.util;


import org.hibernate.type.descriptor.DateTimeUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {
    private DateTimeUtil() {}

    /**
     * Converts a date-time string in the format "yyyy-MM-dd HH:mm:ss" to a Timestamp object.
     * @param dateTimeStr The date-time string in yyyy-MM-dd HH:mm:ss format.
     * @return Timestamp
     * @throws IllegalArgumentException if the date-time string is invalid.
     */
    public static Timestamp stringToTimestamp(String dateTimeStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DateTimeUtils.FORMAT_STRING_TIMESTAMP);
            Date parsedDate = dateFormat.parse(dateTimeStr);
            return new Timestamp(parsedDate.getTime());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format.  Must be yyyy-MM-dd HH:mm:ss.", e);
        }
    }

    /**
     * Checks if a given date string (yyyy-MM-dd format) is not a Friday.
     *
     * @param dateStr The date string in yyyy-MM-dd format.
     * @return True if the date is not a Friday, false otherwise.
     * @throws IllegalArgumentException if the date string is invalid.
     */
    public static boolean isFriday(String dateStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr, DateTimeUtils.DATE_TIME_FORMATTER_DATE);
            return date.getDayOfWeek() == DayOfWeek.FRIDAY; // Checks if the day is NOT Friday
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format.  Must be yyyy-MM-dd.", e);
        }
    }

    public static LocalDateTime getCurrentTimestamp() {
        return LocalDateTime.now();
    }

    /**
     * Converts a date string in the format "yyyy-MM-dd" to a Timestamp object.
     * @param date The date string in yyyy-MM-dd format.
     * @return Timestamp
     * @throws IllegalArgumentException if the date string is invalid.
     */
    public static Timestamp stringDateToTimestamp(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DateTimeUtils.FORMAT_STRING_DATE);
            Date parsedDate = dateFormat.parse(date);
            return new Timestamp(parsedDate.getTime());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format.  Must be yyyy-MM-dd.", e);
        }
    }

    /**
     * Adds one day to the given date string in the format "yyyy-MM-dd" and returns the resulting Timestamp.
     * @param date The date string in yyyy-MM-dd format.
     * @return Timestamp
     * @throws IllegalArgumentException if the date string is invalid.
     */
    public static Timestamp addOneDayCalendar(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(dateFormat.parse(date));
            c.add(Calendar.DATE, 1);
            return new Timestamp(c.getTime().getTime());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format.  Must be yyyy-MM-dd.", e);
        }
    }
}
