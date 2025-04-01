package com.damika.emailclient.command.actions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.damika.emailclient.command.Command;
import com.damika.emailclient.command.CommandContext;
import com.damika.emailclient.factory.implementations.BasicEmailController;
import com.damika.emailclient.model.Email;
import com.damika.emailclient.util.InputValidator;

public class SendEmailCommand implements Command {
    private final @NonNull CommandContext context;

    @EnsuresNonNull({ "this.context" })
    public SendEmailCommand(@NonNull CommandContext context) {
        this.context = context;
    }

    @Override
    public void execute() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();

        context.getIoHandler().printInstructions("Please input your sending email details!\n" +
                "Input format: recipient's email, subject, content");

        @Nullable
        String userInput = context.getIoHandler().getUserInsertedDetails();
        if (!InputValidator.isValidEmailInput(userInput)) {
            context.getIoHandler().printInstructions("Invalid input format. Please follow the correct format.");
            return;
        }

        String[] emailDetails = userInput.split(", ");
        BasicEmailController bec = new BasicEmailController();
        Email email = bec.create();
        email.setRecipient(emailDetails[0]);
        email.setSubject(emailDetails[1]);
        email.setContent(emailDetails[2]);
        email.setSendingDate(dtf.format(now));

        if (context.getEmailService().sendMail(email)) {
            context.getIoHandler().printInstructions("Your email was sent successfully!");
            boolean saved = context.getFileService().saveEmail(email);
            context.getIoHandler()
                    .printInstructions(saved ? "Mail saved in the server." : "Error occurred saving the mail.");
        }
    }
}
