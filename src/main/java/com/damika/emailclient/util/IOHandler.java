package com.damika.emailclient.util;

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
            printInstructions("Please enter your option in a valid range!");
            try {
                String input = reader.readLine();
                return Integer.parseInt(input.trim());
            } catch (IOException e) {
                printInstructions("Error reading input. Please try again.");
            }
        }
    }

    public String getUserInsertedDetails() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            printInstructions("Your input operation failed or was interrupted!");
            return null;
        }
    }
}