package com.damika.emailclient.command.actions;

import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.damika.emailclient.command.Command;
import com.damika.emailclient.command.CommandContext;
import com.damika.emailclient.util.InputValidator;

public class GiveRecipientCountCommand implements Command {
    private final @NonNull CommandContext context;

    @EnsuresNonNull({ "this.context" })
    public GiveRecipientCountCommand(@NonNull CommandContext context) {
        this.context = context;
    }

    @Override
    public void execute() {
        @Nullable
        String[] recipients = context.getFileService().getAllRecipients();

        if (InputValidator.isNullOrEmpty(recipients)) {
            context.getIoHandler().printInstructions("No recipients found.");
            return;
        }
        context.getIoHandler().printInstructions("Number of recipients: " + recipients.length);
    }
}
