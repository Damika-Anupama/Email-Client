package com.damika.emailclient.command.actions;

import java.io.IOException;

import com.damika.emailclient.command.Command;
import com.damika.emailclient.command.CommandContext;

public class ShutdownSystemCommand implements Command {
    private final CommandContext context;

    public ShutdownSystemCommand(CommandContext context) {
        this.context = context;
    }

    @Override
    public void execute() {
        context.getIoHandler().printInstructions("Are you sure you want to shut down the system? (yes/no)");
        try {
            String confirmation = context.getReader().readLine();
            if (confirmation.equalsIgnoreCase("yes")) {
                context.getIoHandler().printInstructions("System Shutdown!");
                System.exit(0);
            } else {
                context.getIoHandler().printInstructions("Shutdown canceled.");
            }
        } catch (IOException e) {
            System.err.println("Error occurred while reading input: " + e.getMessage());
        }
    }
}
