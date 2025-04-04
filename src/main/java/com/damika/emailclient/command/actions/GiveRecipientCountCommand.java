package com.damika.emailclient.command.actions;

import com.damika.emailclient.command.Command;
import com.damika.emailclient.command.CommandContext;

public class GiveRecipientCountCommand implements Command {
    private final CommandContext context;

    public GiveRecipientCountCommand(CommandContext context) {
        this.context = context;
    }

    @Override
    public void execute() {
        String[] recipients = context.getFileService().getAllRecipients();
        context.getIoHandler().printInstructions("Number of recipients: " + recipients.length);
    }
}
