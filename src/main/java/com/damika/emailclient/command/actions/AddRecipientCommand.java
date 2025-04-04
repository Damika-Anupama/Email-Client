package com.damika.emailclient.command.actions;

import java.io.IOException;

import com.damika.emailclient.command.Command;
import com.damika.emailclient.command.CommandContext;
import com.damika.emailclient.util.InputValidator;

public class AddRecipientCommand implements Command {
    private final CommandContext context;
    private final RecipientManager recipientManager;

    public AddRecipientCommand(CommandContext context) {
        this.context = context;
        this.recipientManager = new RecipientManager(context.getFileService(), context.getIoHandler());
    }

    @Override
    public void execute() {
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

                String recipientDetails = context.getIoHandler().getUserInsertedDetails();
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
                    String email = context.getReader().readLine();
                    String recipient = context.getFileService().findRecipientByEmailAddress(email);
                    context.getIoHandler().printInstructions(recipient);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case 3:
                String[] recipients = context.getFileService().getAllRecipients();
                for (String recipient : recipients) {
                    context.getIoHandler().printInstructions(recipient);
                }
                context.getIoHandler().printInstructions("All recipients have been printed!");
                break;

            default:
                context.getIoHandler().printInstructions("Please enter a given value!");
                break;
        }
    }
}