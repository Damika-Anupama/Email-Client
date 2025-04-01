package com.damika.emailclient.command.actions;

import java.io.IOException;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

import com.damika.emailclient.command.Command;
import com.damika.emailclient.command.CommandContext;
import com.damika.emailclient.util.InputValidator;

public class AddRecipientCommand implements Command {
    private final @NonNull CommandContext context;
    private final @NonNull RecipientManager recipientManager;

    @EnsuresNonNull({ "this.context", "this.recipientManager" })
    public AddRecipientCommand(@NonNull CommandContext context) {
        this.context = context;
        this.recipientManager = new RecipientManager(context.getFileService(), context.getIoHandler());
    }

    @Override
    public void execute() {
        context.getIoHandler().printInstructions(
                    "Enter 1: if you want to add a new recipient\n" +
                    "Enter 2: If you want to get a specified recipient by email\n" +
                    "Enter 3: If you want to get all the recipients\n"
                );
        int input = context.getIoHandler().getUserSelectedOption();

        if (!InputValidator.isValidOption(String.valueOf(input), 1, 3)) {
            context.getIoHandler().printInstructions("Invalid option. Please enter a number between 1 and 3.");
            return;
        }

        switch (input) {
            case 1:
                context.getIoHandler().printInstructions("\n" +
                        "input format - \n" +
                        "Official: Nimal,nimal@gmail.com,ceo\n" +
                        "Office_friend: kamal,kamal@gmail.com,clerk,2000/09/08\n" +
                        "Personal: sunil,sunil@gmail.com,<nick-name>,2000/08/03");

                @Nullable
                String recipientDetails = context.getIoHandler().getUserInsertedDetails();

                if (!InputValidator.isValidRecipientInput(recipientDetails)) {
                    context.getIoHandler().printInstructions("Invalid input format. Please follow the correct format.");
                    return;
                }

                String[] split = recipientDetails.split(": ");
                String type = split[0];
                String[] details = split[1].split(",");

                if (!InputValidator.isValidRecipientTypeAndDetails(type, details)) {
                    context.getIoHandler().printInstructions("Invalid recipient type or details. Please try again.");
                    return;
                }

                recipientManager.saveRecipientByType(type, details);
                break;

            case 2:
                context.getIoHandler().printInstructions("Please enter the email of the recipient: ");
                try {
                    @Nullable
                    String email = context.getReader().readLine();
                    if (InputValidator.isNullOrEmpty(email)) {
                        context.getIoHandler().printInstructions("Input was null or empty.");
                        return;
                    }

                    @Nullable
                    String recipient = context.getFileService().findRecipientByEmailAddress(email);
                    if (recipient != null) {
                        context.getIoHandler().printInstructions(recipient);
                    } else {
                        context.getIoHandler().printInstructions("Recipient not found.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case 3:
                @Nullable
                String[] recipients = context.getFileService().getAllRecipients();
                if (recipients == null || recipients.length == 0) {
                    context.getIoHandler().printInstructions("No recipients found!");
                    return;
                }
                for (String recipient : recipients) {
                    if (recipient != null) {
                        context.getIoHandler().printInstructions(recipient);
                    }
                }
                context.getIoHandler().printInstructions("All recipients have been printed!");
                break;

            default:
                context.getIoHandler().printInstructions("Please enter a given value!");
                break;
        }
    }
}