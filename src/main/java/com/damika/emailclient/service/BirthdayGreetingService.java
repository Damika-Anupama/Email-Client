package com.damika.emailclient.service;

public class BirthdayGreetingService {
    private final EmailSendingService emailService;

    public BirthdayGreetingService(EmailSendingService emailService) {
        this.emailService = emailService;
    }

    public void startGreeting() {
        new Thread(emailService, "birthdayWisher").start();
    }
}
