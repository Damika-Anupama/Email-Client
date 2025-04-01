package com.damika.emailclient.command;

import com.damika.emailclient.service.EmailSendingService;
import com.damika.emailclient.service.FileService;
import com.damika.emailclient.util.IOHandler;

import java.io.BufferedReader;

import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

public class CommandContext {
    private final @NonNull FileService fileService;
    private final @NonNull EmailSendingService emailService;
    private final @NonNull BufferedReader reader;
    private final @NonNull IOHandler ioHandler;

    @EnsuresNonNull({"this.fileService", "this.emailService", "this.reader", "this.ioHandler" })
    public CommandContext(@NonNull FileService fileService, @NonNull EmailSendingService emailService,
                          @NonNull BufferedReader reader, @NonNull IOHandler ioHandler) {
        this.fileService = fileService;
        this.emailService = emailService;
        this.reader = reader;
        this.ioHandler = ioHandler;
    }

    @RequiresNonNull("this.fileService")
    public @NonNull FileService getFileService() {
        return fileService;
    }

    @RequiresNonNull("this.emailService")
    public @NonNull EmailSendingService getEmailService() {
        return emailService;
    }

    @RequiresNonNull("this.reader")
    public @NonNull BufferedReader getReader() {
        return reader;
    }

    @RequiresNonNull("this.ioHandler")
    public @NonNull IOHandler getIoHandler() {
        return ioHandler;
    }
}