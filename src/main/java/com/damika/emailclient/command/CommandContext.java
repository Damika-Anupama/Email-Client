package com.damika.emailclient.command;

import com.damika.emailclient.service.EmailSendingService;
import com.damika.emailclient.service.FileService;
import com.damika.emailclient.util.IOHandler;

import java.io.BufferedReader;

public class CommandContext {
    private final FileService fileService;
    private final EmailSendingService emailService;
    private final BufferedReader reader;
    private final IOHandler ioHandler;

    public CommandContext(FileService fileService, EmailSendingService emailService,
            BufferedReader reader, IOHandler ioHandler) {
        this.fileService = fileService;
        this.emailService = emailService;
        this.reader = reader;
        this.ioHandler = ioHandler;
    }

    public FileService getFileService() {
        return fileService;
    }

    public EmailSendingService getEmailService() {
        return emailService;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public IOHandler getIoHandler() {
        return ioHandler;
    }
}