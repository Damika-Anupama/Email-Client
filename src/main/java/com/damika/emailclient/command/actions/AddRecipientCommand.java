package com.damika.emailclient.command.actions;

import java.io.IOException;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.common.value.qual.ArrayLen;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

import com.damika.emailclient.command.Command;
import com.damika.emailclient.command.CommandContext;
import com.damika.emailclient.util.InputValidator;

public class AddRecipientCommand implements Command {
    private final @Nullable CommandContext context;
    private final @Nullable RecipientManager recipientManager;

    @EnsuresNonNull({ "this.context", "this.recipientManager" })
    public AddRecipientCommand(CommandContext context) {
        this.context = context;
        this.recipientManager = new RecipientManager(context.getFileService(), context.getIoHandler());
    }

    @Override
    public void execute() {
        if (context == null || recipientManager == null) {
            throw new IllegalStateException("CommandContext or RecipientManager cannot be null");
        }

        context.getIoHandler().printInstructions(
                "Enter 1: if you want to add a new recipient\n" +
                        "Enter 2: If you want to get a specified recipient by email\n" +
                        "Enter 3: If you want to get all the recipients\n");
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

                if (!InputValidator.isValidRecipientInput(recipientDetails) || recipientDetails == null) {
                    context.getIoHandler().printInstructions("Invalid input format. Please follow the correct format.");
                    return;
                }

                String[] parts = recipientDetails.split(": ");
                if (parts.length != 2) {
                    context.getIoHandler().printInstructions("Invalid format. Must contain a single ': ' to separate type and details.");
                    return;
                }

                String type = parts[0];
                String[] details = parts[1].split(",");
                if (details.length < 3 || details.length > 4) {
                    context.getIoHandler().printInstructions("Invalid format. Not enough details after ': '.");
                    return;
                }

                if (!InputValidator.isValidRecipientTypeAndDetails(type, details)) {
                    context.getIoHandler().printInstructions("Invalid recipient type or details. Please try again.");
                    return;
                }

                recipientManager.saveRecipientByType(type, (@ArrayLen({3, 4}) String[]) details);
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