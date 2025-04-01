package com.damika.emailclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.checkerframework.checker.nullness.qual.NonNull;

import com.damika.emailclient.service.*;
import com.damika.emailclient.command.Command;
import com.damika.emailclient.command.CommandContext;
import com.damika.emailclient.command.actions.AddRecipientCommand;
import com.damika.emailclient.command.actions.SendEmailCommand;
import com.damika.emailclient.command.actions.PrintRecipientsDueToBirthdayCommand;
import com.damika.emailclient.command.actions.PrintEmailsCommand;
import com.damika.emailclient.command.actions.GiveRecipientCountCommand;
import com.damika.emailclient.command.actions.ShutdownSystemCommand;
import com.damika.emailclient.handler.GlobalExceptionHandler;
import com.damika.emailclient.util.InputValidator;
import com.damika.emailclient.util.IOHandler;

public class EmailClient {
    private final @NonNull FileService fileService;
    private final @NonNull EmailSendingService emailService;
    private final @NonNull BufferedReader reader;
    private final @NonNull IOHandler ioHandler;
    private final Map<Integer, Command> commandMap = new HashMap<>();
    private final CommandContext commandContext;

    public EmailClient() {
        this.fileService = Objects.requireNonNull(getFileServiceProvider(), "FileService must not be null");
        this.emailService = Objects.requireNonNull(getEmailServiceProvider(), "EmailSendingService must not be null");
        this.reader = Objects.requireNonNull(getBufferedReaderProvider(), "BufferedReader must not be null");
        this.ioHandler = Objects.requireNonNull(getIOHandlerProvider(), "IOHandler must not be null");
        this.commandContext = new CommandContext(fileService, emailService, reader, ioHandler);
        initializeCommands();
    }
    
    private static @NonNull BufferedReader getBufferedReaderProvider() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    private static @NonNull EmailSendingService getEmailServiceProvider() {
        return new EmailSendingService();
    }

    private static @NonNull FileService getFileServiceProvider() {
        return new FileService();
    }

    private static @NonNull IOHandler getIOHandlerProvider() {
        return new IOHandler();
    }




    private void initializeCommands() {
        commandMap.put(1, new AddRecipientCommand(commandContext));
        commandMap.put(2, new SendEmailCommand(commandContext));
        commandMap.put(3, new PrintRecipientsDueToBirthdayCommand(commandContext));
        commandMap.put(4, new PrintEmailsCommand(commandContext));
        commandMap.put(5, new GiveRecipientCountCommand(commandContext));
        commandMap.put(6, new ShutdownSystemCommand(commandContext));
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void actionController() {
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
            Command command = commandMap.get(option);

            if (!InputValidator.isValidOption(String.valueOf(option), 1, 6)) {
                System.out.println("Invalid option. Please enter a number between 1 and 6.");
                continue;
            }else {
                command.execute();
            }
        }
    }

    private void handleGlobalExceptions() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }

    private void greetRecipients() {
        new Thread(new EmailSendingService(), "birthdayWisher").start();
    }

    public static void main(@NonNull String[] args) {
        EmailClient emailClient = new EmailClient();

        emailClient.handleGlobalExceptions();
        emailClient.greetRecipients();
        emailClient.actionController();
    }
}