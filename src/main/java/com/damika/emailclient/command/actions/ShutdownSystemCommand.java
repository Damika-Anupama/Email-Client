package com.damika.emailclient.command.actions;

import java.io.IOException;

import org.checkerframework.checker.nullness.qual.Nullable;

import com.damika.emailclient.command.Command;
import com.damika.emailclient.command.CommandContext;
import com.damika.emailclient.util.InputValidator;

public class ShutdownSystemCommand implements Command {
    private final @Nullable CommandContext context;

    public ShutdownSystemCommand(CommandContext context) {
        this.context = context;
    }

    @Override
    public void execute() {
        if (context == null) {
            throw new IllegalStateException("CommandContext cannot be null");
        }

        context.getIoHandler().printInstructions("Are you sure you want to shut down the system? (yes/no)");
        try {
            @Nullable
            String confirmation = context.getReader().readLine();
            if (confirmation == null || (InputValidator.isValidInput(confirmation) && confirmation.equalsIgnoreCase("yes"))) {
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
