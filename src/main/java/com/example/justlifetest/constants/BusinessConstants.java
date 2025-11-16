package com.example.justlifetest.constants;

import java.util.Arrays;
import java.util.List;

public class BusinessConstants {

    public static final String SECONDS_FOR_DATE = ":00";
    public static final int BREAK_TIME_IN_MINUTES = 30;
    public static final int BREAK_TIME_IN_MILLISECONDS = BREAK_TIME_IN_MINUTES * 60 * 1000;
    public static final int MIN_CLEANER_COUNT = 1;
    public static final int MAX_CLEANER_COUNT = 3;
    public static final int SERVICE_START_HOUR = 8;
    public static final int SERVICE_END_HOUR = 22;
    public static final List<Integer> SERVICE_HOUR_LIST = Arrays.asList(2, 4);
    public static final Long SYSTEM_USER = 99L;
}
