package com.damika.emailclient.command;

import com.damika.emailclient.service.EmailSendingService;
import com.damika.emailclient.service.FileService;
import com.damika.emailclient.util.IOHandler;

import java.io.BufferedReader;

import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

public class CommandContext {
    private final FileService fileService;
    private final EmailSendingService emailService;
    private final BufferedReader reader;
    private final IOHandler ioHandler;

    @EnsuresNonNull({ "this.fileService", "this.emailService", "this.reader", "this.ioHandler" })
    public CommandContext(FileService fileService, EmailSendingService emailService,
            BufferedReader reader, IOHandler ioHandler) {
        this.fileService = fileService;
        this.emailService = emailService;
        this.reader = reader;
        this.ioHandler = ioHandler;
    }

    @RequiresNonNull("this.fileService")
    public FileService getFileService() {
        return fileService;
    }

    @RequiresNonNull("this.emailService")
    public EmailSendingService getEmailService() {
        return emailService;
    }

    @RequiresNonNull("this.reader")
    public BufferedReader getReader() {
        return reader;
    }

    @RequiresNonNull("this.ioHandler")
    public IOHandler getIoHandler() {
        return ioHandler;
    }
}