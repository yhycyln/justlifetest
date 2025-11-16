package com.example.justlifetest.util;


public class StringUtil {
    private StringUtil() {}

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
