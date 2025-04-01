package com.damika.emailclient.command.actions;

import java.io.IOException;
import java.util.ArrayList;

import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.damika.emailclient.command.Command;
import com.damika.emailclient.command.CommandContext;
import com.damika.emailclient.util.InputValidator;

public class PrintRecipientsDueToBirthdayCommand implements Command {
    private final @NonNull CommandContext context;

    @EnsuresNonNull({ "this.context" })
    public PrintRecipientsDueToBirthdayCommand(@NonNull CommandContext context) {
        this.context = context;
    }

    @Override
    public void execute() {
        System.out.println("Please enter the birthday of the recipients: ");
        System.out.println("input format - yyyy/MM/dd (ex: 2018/09/17)");
        @MonotonicNonNull
        ArrayList<String> recipients = new ArrayList<>();
        try {
            @Nullable
            String bodInput = context.getReader().readLine();
            if (InputValidator.isValidDate(bodInput, "yyyy/MM/dd")) {
                recipients = context.getFileService().findRecipientsByBOD(bodInput);
            } else {
                System.out.println("Invalid or null input. Cannot search for recipients.");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (recipients.size() == 0) {
            System.out.println("No recipient was found, according to the given birthday!");
        } else {
            recipients.forEach(System.out::println);
        }
    }
}
