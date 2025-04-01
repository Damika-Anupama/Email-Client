package com.damika.emailclient.util;

import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IOHandler {
    private final BufferedReader reader;

    public IOHandler() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void printInstructions(String instructions) {
        System.out.println(instructions);
    }

    public int getUserSelectedOption() {
        while (true) {
            System.out.println("Please enter your option in a valid range!");
            try {
                @Nullable
                String input = reader.readLine();
                return Integer.parseInt(input.trim());
            } catch (IOException e) {
                System.out.println("Error reading input. Please try again.");
            }
        }
    }

    public @Nullable String getUserInsertedDetails() {
        try {
            @Nullable
            String input = reader.readLine();
            if (InputValidator.isValidInput(input)) {
                return input;
            } else {
                System.out.println("Invalid input. Please try again.");
                return null;
            }
        } catch (IOException e) {
            System.out.println("Your input operation failed or was interrupted!");
            return null;
        }
    }
}