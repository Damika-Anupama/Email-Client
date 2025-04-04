package com.damika.emailclient.command.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.damika.emailclient.command.Command;
import com.damika.emailclient.command.CommandContext;
import com.damika.emailclient.model.Email;
import com.damika.emailclient.util.InputValidator;

public class PrintEmailsCommand implements Command {
    private final @Nullable CommandContext context;

    @EnsuresNonNull({ "this.context" })
    public PrintEmailsCommand(CommandContext context) {
        this.context = context;
    }

    @Override
    public void execute() {
        if (context == null) {
            throw new IllegalStateException("CommandContext cannot be null");
        }

        context.getIoHandler().printInstructions("Please enter the date when the emails were sent: " +
                "\ninput format - yyyy/MM/dd (ex: 2018/09/17)");

        try {
            @Nullable
            String input = context.getReader().readLine();
            if (!InputValidator.isValidDate(input, "yyyy/MM/dd")) {
                context.getIoHandler()
                        .printInstructions("Invalid input or date format. Please follow the correct format.");
                return;
            }
            if (input == null) {
                context.getIoHandler().printInstructions("Input cannot be null.");
                return;
            }
            ArrayList<Email> emails = context.getFileService().findMail(input);
            emails.stream().filter(Objects::nonNull).forEach(System.out::println);
        } catch (IOException e) {
            context.getIoHandler().printInstructions("Error reading input or retrieving email list.");
            e.printStackTrace();
        }
    }
}
