package com.damika.emailclient.command.actions;

import java.io.IOException;
import java.util.ArrayList;

import com.damika.emailclient.command.Command;
import com.damika.emailclient.command.CommandContext;
import com.damika.emailclient.util.InputValidator;

public class PrintRecipientsDueToBirthdayCommand implements Command {
    private final CommandContext context;

    public PrintRecipientsDueToBirthdayCommand(CommandContext context) {
        this.context = context;
    }

    @Override
    public void execute() {

        context.getIoHandler().printInstructions(
                "Please enter the birthday of the recipients: \n" +
                        "input format - yyyy/MM/dd (ex: 2018/09/17)");

        ArrayList<String> recipients = new ArrayList<>();
        try {
            String bodInput = context.getReader().readLine();
            if (InputValidator.isValidDate(bodInput, "yyyy/MM/dd")) {
                recipients = context.getFileService().findRecipientsByBOD(bodInput);
            } else {
                context.getIoHandler().printInstructions("Invalid or null input. Cannot search for recipients.");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (recipients.size() == 0) {
            context.getIoHandler().printInstructions("No recipient was found, according to the given birthday!");
        } else {
            recipients.forEach(System.out::println);
        }
    }
}
