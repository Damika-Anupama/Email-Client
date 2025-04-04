package com.damika.emailclient.command.actions;

import java.io.IOException;
import java.util.ArrayList;

import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;

import org.checkerframework.checker.nullness.qual.Nullable;

import com.damika.emailclient.command.Command;
import com.damika.emailclient.command.CommandContext;
import com.damika.emailclient.util.InputValidator;

public class PrintRecipientsDueToBirthdayCommand implements Command {
    private final @Nullable CommandContext context;

    @EnsuresNonNull({ "this.context" })
    public PrintRecipientsDueToBirthdayCommand(CommandContext context) {
        this.context = context;
    }

    @Override
    public void execute() {
        if (context == null) {
            throw new IllegalStateException("CommandContext cannot be null");
        }
        
        context.getIoHandler().printInstructions(
                "Please enter the birthday of the recipients: \n" +
                        "input format - yyyy/MM/dd (ex: 2018/09/17)");

        @MonotonicNonNull
        ArrayList<String> recipients = new ArrayList<>();
        try {
            @Nullable
            String bodInput = context.getReader().readLine();
            if (InputValidator.isValidDate(bodInput, "yyyy/MM/dd")) {
                if (bodInput != null) {
                    recipients = context.getFileService().findRecipientsByBOD(bodInput);
                } else {
                    context.getIoHandler().printInstructions("Invalid or null input. Cannot search for recipients.");
                    return;
                }
            } else {
                context.getIoHandler().printInstructions("Invalid or null input. Cannot search for recipients.");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (recipients.size() == 0) {
            context.getIoHandler().printInstructions("No recipient was found, according to the given birthday!");
        } else {
            recipients.forEach(System.out::println);
        }
    }
}
