package com.damika.emailclient.util;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class InputValidator {

    private static final Map<String, Integer> RECIPIENT_TYPE_FIELD_COUNT = new HashMap<>();

    static {
        RECIPIENT_TYPE_FIELD_COUNT.put("Official", 3);
        RECIPIENT_TYPE_FIELD_COUNT.put("Office_friend", 4);
        RECIPIENT_TYPE_FIELD_COUNT.put("Personal", 4);
    }

    public static boolean isNullOrEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }

    public static boolean isNullOrEmpty(String[] input) {
        return input == null || input.length == 0 || 
               java.util.Arrays.stream(input).allMatch(str -> str == null || str.trim().isEmpty());
    }

    public static boolean isValidRecipientInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        if (!input.contains(": ")) {
            return false;
        }
        String[] split = input.split(": ");
        return split.length == 2;
    }

    public static boolean isValidRecipientTypeAndDetails(String type, String[] details) {
        if (!RECIPIENT_TYPE_FIELD_COUNT.containsKey(type)) {
            return false;
        }
        int expectedLength = RECIPIENT_TYPE_FIELD_COUNT.get(type);
        return details.length == expectedLength;
    }

    public static boolean isValidEmailInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        String[] split = input.split(", ");
        return split.length >= 3;
    }

    public static boolean isValidOption(String input, int min, int max) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        try {
            int option = Integer.parseInt(input.trim());
            return option >= min && option <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidInput(String input) {
        return !isNullOrEmpty(input);
    }

    public static boolean isValidDate(String input, String dateFormat) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
            LocalDateTime.parse(input.trim(), formatter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}