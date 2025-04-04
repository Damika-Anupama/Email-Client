package com.damika.emailclient.command.actions;

import java.util.Map;

import org.checkerframework.checker.nullness.qual.Nullable;

import com.damika.emailclient.command.Command;
import com.damika.emailclient.command.CommandContext;
import com.damika.emailclient.util.InputValidator;

public class ActionController {
    private final @Nullable CommandContext commandContext;
    private final @Nullable Map<Integer, Command> commandMap;

    public ActionController(CommandContext commandContext, Map<Integer, Command> commandMap) {
        this.commandContext = commandContext;
        this.commandMap = commandMap;
    }

    public void start() {
        if (commandContext == null || commandMap == null) {
            throw new IllegalStateException("CommandContext or CommandMap cannot be null");
        }
        while (true) {
            commandContext.getIoHandler().printInstructions(
                    "\n Enter option type: \n"
                            + "1 - Adding a new recipient\n"
                            + "2 - Sending an email\n"
                            + "3 - Printing out all the recipients who have birthdays\n"
                            + "4 - Printing out details of all the emails sent\n"
                            + "5 - Printing out the number of recipients in this application\n"
                            + "6 - exit\n"
            );

            int option = commandContext.getIoHandler().getUserSelectedOption();
            if (!InputValidator.isValidOption(String.valueOf(option), 1, 6) ) {
                commandContext.getIoHandler().printInstructions("Invalid option. Please enter a number between 1 and 6.");
                continue;
            }

            Command command = commandMap.get(option);
            if (command == null) {
                commandContext.getIoHandler().printInstructions("Invalid command. Please try again.");
                continue;
            }
            
            command.execute();
        }
    }
}
