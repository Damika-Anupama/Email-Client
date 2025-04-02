package com.damika.emailclient.command;

import java.util.HashMap;
import java.util.Map;
import com.damika.emailclient.command.actions.*;

public class CommandInitializer {
    public static Map<Integer, Command> initializeCommands(CommandContext commandContext) {
        Map<Integer, Command> commandMap = new HashMap<>();
        commandMap.put(1, new AddRecipientCommand(commandContext));
        commandMap.put(2, new SendEmailCommand(commandContext));
        commandMap.put(3, new PrintRecipientsDueToBirthdayCommand(commandContext));
        commandMap.put(4, new PrintEmailsCommand(commandContext));
        commandMap.put(5, new GiveRecipientCountCommand(commandContext));
        commandMap.put(6, new ShutdownSystemCommand(commandContext));
        return commandMap;
    }
}
