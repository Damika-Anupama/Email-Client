package com.damika.emailclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.damika.emailclient.service.*;
import com.damika.emailclient.factory.implementations.OfficialRecipientController;
import com.damika.emailclient.factory.implementations.OfficialRecipientFriendController;
import com.damika.emailclient.factory.implementations.PersonalRecipientController;
import com.damika.emailclient.model.Email;
import com.damika.emailclient.model.Official_Recipient;
import com.damika.emailclient.model.Official_Recipient_Friend;
import com.damika.emailclient.model.Personal_Recipient;
import com.damika.emailclient.factory.implementations.BasicEmailController;
import com.damika.emailclient.handler.GlobalExceptionHandler;
import com.damika.emailclient.util.InputValidator;

public class EmailClient {
    private final @NonNull FileService fileService;
    private final @NonNull EmailSendingService emailService;
    private final @NonNull BufferedReader reader;

    public EmailClient() {
        this.fileService = Objects.requireNonNull(getFileServiceProvider(), "FileService must not be null");
        this.emailService = Objects.requireNonNull(getEmailServiceProvider(), "EmailSendingService must not be null");
        this.reader = Objects.requireNonNull(getBufferedReaderProvider(), "BufferedReader must not be null");
    }

    private void greetRecipients() {
        new Thread(new EmailSendingService(), "birthdayWisher").start();
    }

    private static @NonNull BufferedReader getBufferedReaderProvider() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    private static @NonNull EmailSendingService getEmailServiceProvider() {
        return new EmailSendingService();
    }

    private static @NonNull FileService getFileServiceProvider() {
        return new FileService();
    }

    private void printInstructions(@NonNull String instructions) {
        System.out.println(instructions);
    }

    private void saveRecipient(@NonNull String data) {
        System.out.println(
                fileService.saveRecipient(data) ? "Client saved successfully!" : "Try again!");
    }

    private void saveRecipientByType(@NonNull String type, @NonNull String[] details) {
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
                System.out.println("Unknown recipient type. Use 'Official', 'Office_friend', or 'Personal'.");
                return;
        }

        if (details.length != expectedLength) {
            System.out.println(
                    "Invalid number of details for type '" + type + "'. Expected " + expectedLength + " fields.");
            return;
        }

        Object recipient = createRecipient(type, details);
        if (recipient != null) {
            saveRecipient(recipient.toString());
        }
    }

    private @Nullable Object createRecipient(@NonNull String type, @NonNull String[] details) {
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
                System.out.println("Unknown recipient type. Cannot create recipient.");
                return null;
        }
    }

    private void addNewCustomer1() {
        printInstructions("Enter 1: if you want to add a new recipient\n" +
                "Enter 2: If you want to get a specified recipient by email\n" +
                "Enter 3: If you want to get all the recipients\n");
        int input = giveUserSelectedOption();
        if (!InputValidator.isValidOption(String.valueOf(input), 1, 3)) {
            System.out.println("Invalid option. Please enter a number between 1 and 3.");
            return;
        }

        switch (input) {
            case 1:
                printInstructions("\n" +
                        "input format - \n" +
                        "Official: Nimal,nimal@gmail.com,ceo\n" +
                        "Office_friend: kamal,kamal@gmail.com,clerk,2000/09/08\n" +
                        "Personal: sunil,sunil@gmail.com,<nick-name>,2000/08/03");

                @Nullable
                String recipientDetails = giveUserInsertedDetails();

                if (!InputValidator.isValidRecipientInput(recipientDetails)) {
                    System.out.println("Invalid input format. Please follow the correct format.");
                    return;
                }

                String[] split = recipientDetails.split(": ");
                String type = split[0];
                String[] details = split[1].split(",");

                if (!InputValidator.isValidRecipientTypeAndDetails(type, details)) {
                    System.out.println("Invalid recipient type or details. Please try again.");
                    return;
                }

                saveRecipientByType(type, details);
                break;

            case 2:
                System.out.print("Please enter the email of the recipient: ");
                try {
                    @Nullable
                    String email = reader.readLine();
                    if (InputValidator.isNullOrEmpty(email)) {
                        System.out.println("Input was null or empty.");
                        return;
                    }

                    @Nullable
                    String recipient = fileService.findRecipientByEmailAddress(email);
                    if (recipient != null) {
                        System.out.println(recipient);
                    } else {
                        System.out.println("Recipient not found.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case 3:
                @Nullable
                String[] recipients = fileService.getAllRecipients();
                if (recipients == null || recipients.length == 0) {
                    System.out.println("No recipients found!");
                    return;
                }
                for (String recipient : recipients) {
                    if (recipient != null) {
                        System.out.println(recipient);
                    }
                }
                System.out.println();
                break;

            default:
                System.out.println("Please enter a given value!");
                break;
        }
    }

    private void sendEmail2() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();

        printInstructions("Please input your sending email details!\n" +
                "Input format: recipient's email, subject, content");

        @Nullable
        String userInput = giveUserInsertedDetails();
        if (!InputValidator.isValidEmailInput(userInput)) {
            System.out.println("Invalid input format. Please follow the correct format.");
            return;
        }

        String[] emailDetails = userInput.split(", ");
        BasicEmailController bec = new BasicEmailController();
        Email email = bec.create();
        email.setRecipient(emailDetails[0]);
        email.setSubject(emailDetails[1]);
        email.setContent(emailDetails[2]);
        email.setSendingDate(dtf.format(now));

        if (emailService.sendMail(email)) {
            System.out.println("Your email was sent successfully!");
            boolean saved = fileService.saveEmail(email);
            System.out.println(saved ? "Mail saved in the server." : "Error occurred saving the mail.");
        }
    }

    private void printRecipientsDueToBirthday3() {
        System.out.println("Please enter the birthday of the recipients: ");
        System.out.println("input format - yyyy/MM/dd (ex: 2018/09/17)");
        @MonotonicNonNull
        ArrayList<String> recipients = new ArrayList<>();
        try {
            @Nullable
            String bodInput = reader.readLine();
            if (InputValidator.isValidDate(bodInput, "yyyy/MM/dd")) {
                recipients = fileService.findRecipientsByBOD(bodInput);
            } else {
                System.out.println("Invalid or null input. Cannot search for recipients.");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (recipients.size() == 0) {
            System.out.println("No recipient was found, according to the given birthday!");
        } else {
            recipients.forEach(System.out::println);
        }
    }

    private void printEmails4() {

        System.out.print("Please enter the date when the emails were sent: ");
        System.out.println("input format - yyyy/MM/dd (ex: 2018/09/17)");
        try {
            @Nullable
            String input = reader.readLine();
            if (!InputValidator.isValidDate(input, "yyyy/MM/dd")) {
                System.out.println("Invalid input or date format. Please follow the correct format.");
                return;
            }
            ArrayList<Email> emails = fileService.findMail(input);
            emails.stream().filter(Objects::nonNull).forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Error reading input or retrieving email list.");
            e.printStackTrace();
        }
    }

    private void giveRecipientCount5() {
        @Nullable
        String[] recipients = fileService.getAllRecipients();

        if (InputValidator.isNullOrEmpty(recipients)) {
            System.out.println("No recipients found.");
            return;
        }
        System.out.println("Number of recipients: " + recipients.length);
    }

    private void shutdownSystem6() {
        System.out.println("Are you sure you want to shut down the system? (yes/no)");
        try {
            @Nullable
            String confirmation = reader.readLine();
            if (InputValidator.isValidInput(confirmation) && confirmation.equalsIgnoreCase("yes")) {
                System.out.println("System Shutdown!");
                System.exit(0);
            } else {
                System.out.println("Shutdown canceled.");
            }
        } catch (IOException e) {
            System.err.println("Error occurred while reading input: " + e.getMessage());
        }
    }

    private int giveUserSelectedOption() {
        while (true) {
            System.out.println("Please enter your option in a valid range!");
            try {
                @Nullable
                String input = reader.readLine();
                return Integer.parseInt(input.trim());
            } catch (IOException e) {
                System.out.println("Error reading input. Please try again.");
            }
        }
    }

    private @Nullable String giveUserInsertedDetails() {
        try {
            @Nullable
            String input = reader.readLine();
            if (InputValidator.isValidInput(input)) {
                return input;
            } else {
                System.out.println("Invalid input. Please try again.");
                return null;
            }
        } catch (IOException e) {
            System.out.println("Your input operation failed or was interrupted!");
            return null;
        }
    }

    private void selectOptions(int option) {   
        switch (option) {
            case 1:
                addNewCustomer1();
                break;
            case 2:
                sendEmail2();
                break;
            case 3:
                printRecipientsDueToBirthday3();
                break;
            case 4:
                printEmails4();
                break;
            case 5:
                giveRecipientCount5();
                break;
            case 6:
                shutdownSystem6();
                break;
            default:
                System.out.println("Please enter a relevant number to proceed !");
                break;
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void actionController() {
        while (true) {
            printInstructions("\n Enter option type: \n"
                    + "1 - Adding a new recipient\n"
                    + "2 - Sending an email\n"
                    + "3 - Printing out all the recipients who have birthdays\n"
                    + "4 - Printing out details of all the emails sent\n"
                    + "5 - Printing out the number of recipients in this application\n"
                    + "6 - exit\n");
    
            int option = giveUserSelectedOption();
            if (!InputValidator.isValidOption(String.valueOf(option), 1, 6)) {
                System.out.println("Invalid option. Please enter a number between 1 and 6.");
                continue;
            }
            selectOptions(option);
        }
    }

    private void handleGlobalExceptions() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }

    public static void main(@NonNull String[] args) {
        EmailClient emailClient = new EmailClient();

        emailClient.handleGlobalExceptions();
        emailClient.greetRecipients();
        emailClient.actionController();
    }
}