package com.damika.emailclient.command.actions;

import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.damika.emailclient.command.Command;
import com.damika.emailclient.command.CommandContext;
import com.damika.emailclient.util.InputValidator;

public class GiveRecipientCountCommand implements Command {
    private final @Nullable CommandContext context;

    @EnsuresNonNull({ "this.context" })
    public GiveRecipientCountCommand(CommandContext context) {
        this.context = context;
    }

    @Override
    public void execute() {
        if (context == null) {
            throw new IllegalStateException("CommandContext cannot be null");
        }

        @Nullable
        String[] recipients = context.getFileService().getAllRecipients();
        if (recipients == null) {
            context.getIoHandler().printInstructions("No recipients found.");
            return;
        }
        if (InputValidator.isNullOrEmpty(recipients)) {
            context.getIoHandler().printInstructions("No recipients found.");
            return;
        }
        context.getIoHandler().printInstructions("Number of recipients: " + recipients.length);
    }
}
