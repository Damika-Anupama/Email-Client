package com.damika.emailclient.command.actions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.damika.emailclient.command.Command;
import com.damika.emailclient.command.CommandContext;
import com.damika.emailclient.factory.implementations.BasicEmailController;
import com.damika.emailclient.model.Email;

public class SendEmailCommand implements Command {
    private final CommandContext context;

    public SendEmailCommand(CommandContext context) {
        this.context = context;
    }

    @Override
    public void execute() {        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();

        context.getIoHandler().printInstructions("Please input your sending email details!\n" +
                "Input format: recipient's email, subject, content");

        String userInput = context.getIoHandler().getUserInsertedDetails();
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
