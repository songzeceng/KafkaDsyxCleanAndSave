package com.profile.util;

import java.util.Random;

public class TextUtils {
    private static int sIDLength = -1;
    private static char[] sCHARACTERS = "0123456789abcdef".toCharArray();

    public static boolean isEmpty(String s) {
        return s == null || s.trim().equals("");
    }

    public static boolean isNullValue(String s) {
        return isEmpty(s) || s.toLowerCase().equals("null");
    }

    public static boolean IDLengthSet() {
        return sIDLength > 0;
    }

    public static void setIDLength(int length) {
        sIDLength = length;
    }

    public static String generateRandomID() {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();

        int firstIndex = -1;
        while (firstIndex < 1) {
            firstIndex = Math.abs(random.nextInt()) % sCHARACTERS.length;
        }

        builder.append(sCHARACTERS[firstIndex]);

        for (int i = 1; i < sIDLength; i++) {
            int index = Math.abs(random.nextInt()) % sCHARACTERS.length;
            builder.append(sCHARACTERS[index]);
        }

        return builder.toString();
    }

    public static boolean stringInArray(String target, String[] values) {
        for (String value : values) {
            if (value.equals(target)) {
                return true;
            }
        }

        return false;
    }
}
