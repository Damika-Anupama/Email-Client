package com.damika.emailclient.command.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.damika.emailclient.command.Command;
import com.damika.emailclient.command.CommandContext;
import com.damika.emailclient.model.Email;
import com.damika.emailclient.util.InputValidator;

public class PrintEmailsCommand implements Command {
    private final @NonNull CommandContext context;

    @EnsuresNonNull({ "this.context" })
    public PrintEmailsCommand(@NonNull CommandContext context) {
        this.context = context;
    }

    @Override
    public void execute() {
        System.out.print("Please enter the date when the emails were sent: ");
        System.out.println("input format - yyyy/MM/dd (ex: 2018/09/17)");
        try {
            @Nullable
            String input = context.getReader().readLine();
            if (!InputValidator.isValidDate(input, "yyyy/MM/dd")) {
                System.out.println("Invalid input or date format. Please follow the correct format.");
                return;
            }
            ArrayList<Email> emails = context.getFileService().findMail(input);
            emails.stream().filter(Objects::nonNull).forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Error reading input or retrieving email list.");
            e.printStackTrace();
        }
    }
}
