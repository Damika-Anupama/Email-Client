package com.damika.emailclient.command.actions;

import com.damika.emailclient.factory.implementations.OfficialRecipientController;
import com.damika.emailclient.factory.implementations.OfficialRecipientFriendController;
import com.damika.emailclient.factory.implementations.PersonalRecipientController;
import com.damika.emailclient.model.Official_Recipient;
import com.damika.emailclient.model.Official_Recipient_Friend;
import com.damika.emailclient.model.Personal_Recipient;
import com.damika.emailclient.service.FileService;
import com.damika.emailclient.util.IOHandler;

public class RecipientManager {
    private final FileService fileService;
    private final IOHandler ioHandler;

    public RecipientManager(FileService fileService, IOHandler ioHandler) {
        this.fileService = fileService;
        this.ioHandler = ioHandler;
    }

    public void saveRecipient(String data) {
        ioHandler.printInstructions(
                fileService.saveRecipient(data) ? "Client saved successfully!" : "Try again!");
    }

    public void saveRecipientByType(String type, String[] details) {
        int expectedLength;
        switch (type) {
            case "Official":
                expectedLength = 3;
                break;
            case "Office_friend":
            case "Personal":
                expectedLength = 4;
                break;
            default:
                ioHandler.printInstructions("Unknown recipient type. Use 'Official', 'Office_friend', or 'Personal'.");
                return;
        }

        if (details.length != expectedLength) {
            ioHandler.printInstructions(
                    "Invalid number of details for type '" + type + "'. Expected " + expectedLength + " fields.");
            return;
        }

        Object recipient = createRecipient(type, details);
        saveRecipient(recipient.toString());
    }

    private Object createRecipient(String type, String[] details) {
        switch (type) {
            case "Official":
                OfficialRecipientController orc = new OfficialRecipientController();
                Official_Recipient or = (Official_Recipient) orc.create();
                or.initialize(details[0], details[1], details[2]);
                return or;

            case "Office_friend":
                OfficialRecipientFriendController orfc = new OfficialRecipientFriendController();
                Official_Recipient_Friend orf = (Official_Recipient_Friend) orfc.create();
                orf.initialize(details[0], details[1], details[2], details[3]);
                return orf;

            case "Personal":
                PersonalRecipientController prc = new PersonalRecipientController();
                Personal_Recipient pr = (Personal_Recipient) prc.create();
                pr.initialize(details[0], details[1], details[2], details[3]);
                return pr;

            default:
                ioHandler.printInstructions("Unknown recipient type. Cannot create recipient.");
                return null;
        }
    }
}
