package com.damika.emailclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

import org.checkerframework.checker.nullness.qual.NonNull;

import com.damika.emailclient.service.EmailSendingService;
import com.damika.emailclient.service.FileService;
import com.damika.emailclient.service.BirthdayGreetingService;
import com.damika.emailclient.command.Command;
import com.damika.emailclient.command.CommandContext;
import com.damika.emailclient.command.actions.*;
import com.damika.emailclient.command.CommandInitializer;
import com.damika.emailclient.util.ExceptionHandlerUtil;
import com.damika.emailclient.util.IOHandler;

public class EmailClient {
    private final @NonNull IOHandler ioHandler;
    private final @NonNull BufferedReader reader;
    private final @NonNull FileService fileService;
    private final @NonNull EmailSendingService emailService;
    private final @NonNull CommandContext commandContext;
    private final @NonNull Map<Integer, Command> commandMap;

    public EmailClient() {
        this.ioHandler = new IOHandler();
        this.reader = new BufferedReader(new InputStreamReader(System.in));

        this.fileService = new FileService(ioHandler);
        this.emailService = new EmailSendingService(ioHandler, fileService);

        this.commandContext = new CommandContext(fileService, emailService, reader, ioHandler);
        this.commandMap = CommandInitializer.initializeCommands(commandContext);
    }

    public static void main(@NonNull String[] args) {
        new EmailClient().start();
    }

    private void start() {
        ExceptionHandlerUtil.setupGlobalExceptionHandler();
        new BirthdayGreetingService(emailService).startGreeting();
        new ActionController(commandContext, commandMap).start();
    }
}
