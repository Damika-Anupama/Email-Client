package com.damika.emailclient;

/**
 * Name : P.T.D.A.Nanayakkara
 * Index Number : 200411N
 **/

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.checkerframework.checker.nullness.qual.*;

/*Model classes*/
class Recipient {
    private @Nullable String name;
    private @Nullable String email;

    public Recipient() {
    }

    public @Nullable String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    public @Nullable String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Recipient{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

class Official_Recipient extends Recipient {
    private @Nullable String designation;

    public Official_Recipient() {
    }

    public @Nullable String getDesignation() {
        return designation;
    }

    public void setDesignation(@Nullable String designation) {
        this.designation = designation;
    }

    @Override
    public String toString() {
        return "Official: " +
                super.getName() + ',' +
                super.getEmail() + ',' +
                designation;
    }
}

class Official_Recipient_Friend extends Official_Recipient {
    private @Nullable String birthday;

    public Official_Recipient_Friend() {
    }

    public void setBirthday(@Nullable String birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Office_friend: " +
                super.getName() + ',' +
                super.getEmail() + ',' +
                super.getDesignation() + ',' +
                birthday;
    }
}

class Personal_Recipient extends Recipient {
    private @Nullable String nickname;
    private @Nullable String birthday;

    public void setNickname(@Nullable String nickname) {
        this.nickname = nickname;
    }

    public void setBirthday(@Nullable String birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Personal: " +
                super.getName() + ',' +
                nickname + ',' +
                super.getEmail() + ',' +
                birthday;
    }
}

class Email implements Serializable {
    private @Nullable String recipient;
    private @Nullable String subject;
    private @Nullable String content;
    private @Nullable String sendingDate;

    public Email() {
    }

    public Email(@Nullable String recipient, @Nullable String subject, @Nullable String content,
            @Nullable String sendingDate) {
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
        this.sendingDate = sendingDate;
    }

    public @Nullable String getRecipient() {
        return recipient;
    }

    public void setRecipient(@Nullable String recipient) {
        this.recipient = recipient;
    }

    public @Nullable String getSubject() {
        return subject;
    }

    public void setSubject(@Nullable String subject) {
        this.subject = subject;
    }

    public @Nullable String getContent() {
        return content;
    }

    public void setContent(@Nullable String content) {
        this.content = content;
    }

    public @Nullable String getSendingDate() {
        return sendingDate;
    }

    public void setSendingDate(@Nullable String sendingDate) {
        this.sendingDate = sendingDate;
    }

    @Override
    public String toString() {
        return "recipient=" + recipient +
                ", subject=" + subject +
                ", content=" + content +
                ", sentDateTime=" + sendingDate;
    }
}

/* Apply Factory Method DP for model class creation */
interface NewRecipientCreator {
    @NonNull
    Recipient create();
}

interface NewEmailCreator {
    @NonNull
    Email create();
}

class OfficialRecipientCreator implements NewRecipientCreator {
    @Override
    public @NonNull Recipient create() {
        return new Official_Recipient();
    }
}

class OfficialRecipientFriendCreator implements NewRecipientCreator {
    @Override
    public @NonNull Recipient create() {
        return new Official_Recipient_Friend();
    }
}

class PersonalRecipientCreator implements NewRecipientCreator {
    @Override
    public @NonNull Recipient create() {
        return new Personal_Recipient();
    }
}

class EmailCreator implements NewEmailCreator {
    @Override
    public @NonNull Email create() {
        return new Email();
    }
}

abstract class RecipientController {
    public abstract @NonNull NewRecipientCreator giveRecipientObject();

    public @NonNull Recipient create() {
        @NonNull
        NewRecipientCreator creator = giveRecipientObject();
        return creator.create();
    }
}

abstract class EmailController {
    public abstract @NonNull NewEmailCreator giveEmailObject();

    public @NonNull Email create() {
        @NonNull
        NewEmailCreator creator = giveEmailObject();
        return creator.create();
    }
}

class OfficialRecipientController extends RecipientController {
    @Override
    public @NonNull OfficialRecipientCreator giveRecipientObject() {
        return new OfficialRecipientCreator();
    }
}

class OfficialRecipientFriendController extends RecipientController {
    @Override
    public @NonNull NewRecipientCreator giveRecipientObject() {
        return new OfficialRecipientFriendCreator();
    }
}

class PersonalRecipientController extends RecipientController {
    @Override
    public @NonNull NewRecipientCreator giveRecipientObject() {
        return new PersonalRecipientCreator();
    }
}

class BasicEmailController extends EmailController {
    @Override
    public @NonNull NewEmailCreator giveEmailObject() {
        return new EmailCreator();
    }
}

/* File Service */
class FileService {
    // path can be absolute or relative.
    final static Path clientList = Paths.get("data/ClientList.txt");
    final static Path emailList = Paths.get("data/EmailList.txt");

    /* Recipient Service <- uses IO(java nio package) */
    public boolean saveRecipient(@NonNull String recipient) {
        @Nullable
        String existing = findRecipientByEmailAddress(recipient.split(": ")[1].split(",")[1]);
        if (Files.exists(clientList) && existing != null) {
            // check whether the client exists (by user email) in the file, then
            System.out.println("This user already exists!");
            return false;
        }

        /* If the clientList.txt file doesn't exist create it and append to it. */
        try (BufferedWriter writer = Files.newBufferedWriter(
                clientList,
                StandardCharsets.UTF_8,
                StandardOpenOption.APPEND,
                StandardOpenOption.CREATE)) {
            writer.write("\n" + recipient);
            System.out.println("Successfully inserted the recipient!");
            return true;
        } catch (IOException e) {
            System.out.println("One of relevant files doesn't exists, Please contact the developer!");
            return false;
        }
    }

    public @Nullable String @Nullable [] getAllRecipients() {
        if (!Files.exists(clientList)) {
            System.out.println("No Recipient exists!");
            return null;
        }
        try {
            byte[] bytes = Files.readAllBytes(clientList);
            String recipientFileData = new String(bytes);
            return recipientFileData.split("\n");
        } catch (IOException e) {
            System.out.println("There is a failure during reading the recipients, Please contact the developer!");
            return null;
        }
    }

    // to find the same email address has been input before
    public @Nullable String findRecipientByEmailAddress(@Nullable String email) {
        @Nullable
        String[] recipients = getAllRecipients();
        if (recipients == null)
            return null;
        for (String recipient : recipients) {
            if (recipient == null)
                continue;
            String recipientEmail = recipient.split(": ")[1].split(",")[1];
            if (Objects.equals(email, recipientEmail)) {
                return recipient;
            }
        }
        return null;
    }

    public @NonNull ArrayList<String> findRecipientsByBOD(@NonNull String bod) {
        ArrayList<String> recipients = new ArrayList<>();
        @Nullable
        String[] allRecipients = getAllRecipients();
        if (allRecipients == null)
            return recipients;
        for (String recipient : allRecipients) {
            if (recipient == null)
                continue;
            String[] recipientDetails = recipient.split(": ")[1].split(",");
            if (recipientDetails.length == 4 && recipientDetails[3].equals(bod)) {
                recipients.add(recipient);
            }
        }
        return recipients;
    }

    /* Email Service <- uses serialization */
    public boolean saveEmail(@NonNull Email email) {
        boolean result = false;
        try (FileOutputStream fileStream = new FileOutputStream(String.valueOf(emailList));
                ObjectOutputStream os = new ObjectOutputStream(fileStream)) {
            os.writeObject(email);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public @NonNull ArrayList<Email> findMail(@NonNull String sentDate) throws IOException, ClassNotFoundException {
        ArrayList<Email> emails = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(String.valueOf(emailList));
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            Email email;
            while ((email = (Email) ois.readObject()) != null) {
                @Nullable
                String emailDate = email.getSendingDate();
                if (sentDate.equals(emailDate)) {
                    emails.add(email);
                }
            }
        } catch (EOFException ignored) {
        }
        return emails;
    }
}

/* Email Sending Service */
class EmailSendingService implements Runnable {
    public boolean sendMail(@NonNull Email email) {

        String sender = "palindrome.penguin.unity.clan@gmail.com";
        String password = "hlfmabeyiopoxzov";
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";

        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", host);
        props.put("mail.smtp.user", sender);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        FileService file_service = new FileService();

        try {
            String recipient = email.getRecipient();
            String subject = email.getSubject();
            String content = email.getContent();

            if (recipient == null || subject == null || content == null) {
                System.out.println("Error: recipient, subject, or content is null. Email not sent.");
                return false;
            }

            message.setFrom(new InternetAddress(sender));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(content);

            Transport transport = session.getTransport("smtp");
            transport.connect(host, sender, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            file_service.saveEmail(email);
            return true;
        } catch (MessagingException ae) {
            ae.printStackTrace();
            return false;
        }
    }

    @Override
    public void run() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String today = dtf.format(now);
        FileService file_service = new FileService();
        dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        @Nullable String[] recipients = file_service.getAllRecipients();
        if (recipients == null) {
            System.out.println("No recipients to process.");
            return;
        }
        for (String s : recipients) {
            if (s == null) continue;
            String[] split1 = s.split(": ");
            if (split1.length < 2) {
                System.err.println("Invalid recipient format: " + s);
                continue; // Skip this recipient and move to the next one
            }
            String[] split2 = split1[1].split(",");
            if (split2.length < 4) {
                System.err.println("Invalid recipient details: " + Arrays.toString(split2));
                continue; // Skip this recipient and move to the next one
            }
            if (split2[3].substring(5).equals(today)) {
                String content = null, type = split1[0];
                if (type.equals("Personal")) {
                    content = "hugs and love on your birthday. Damika";
                } else if (type.equals("Office_friend")) {
                    content = "Wish you a Happy Birthday. Damika";
                }
                // send the birthday wish from here (from another method)

                Email email = new Email(split2[1], "Surprise from Damika", content, dtf.format(now));
                sendMail(email);
                file_service.saveEmail(email);
            }
        }

    }
}

class Handler1 implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.err.println("Uncaught exception in thread: " + t.getName());
        e.printStackTrace();
    }
}

public class EmailClient {
    private final @NonNull FileService fileService;
    private final @NonNull EmailSendingService emailService;
    private final @NonNull BufferedReader reader;

    @SuppressWarnings("method.invocation")
    public EmailClient() {
        this.fileService = getFileServiceProvider();
        this.emailService = getEmailServiceProvider();
        this.reader = getBufferedReaderProvider();
    }

    private void greetRecipients() {
        new Thread(new EmailSendingService(), "birthdayWisher").start();
    }

    private @NonNull BufferedReader getBufferedReaderProvider() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    private @NonNull EmailSendingService getEmailServiceProvider() {
        return new EmailSendingService();
    }

    private @NonNull FileService getFileServiceProvider() {
        return new FileService();
    }

    private void printInstructions(@NonNull String instructions) {
        System.out.println(instructions);
    }

    private void saveRecipient(@NonNull String data) {
        System.out.println(
                fileService.saveRecipient(data) ? "Client saved successfully!" : "Try again!");
    }

    private void saveOfficialRecipient(@NonNull String[] split1) {
        OfficialRecipientController orc = new OfficialRecipientController();
        Official_Recipient or = (Official_Recipient) orc.create();

        or.setName(split1[0]);
        or.setEmail(split1[1]);
        or.setDesignation(split1[2]);
        saveRecipient(or.toString());
    }

    private void saveOfficialFriendRecipient(@NonNull String[] split1) {
        OfficialRecipientFriendController orfc = new OfficialRecipientFriendController();
        Official_Recipient_Friend orf = (Official_Recipient_Friend) orfc.create();
        orf.setName(split1[0]);
        orf.setEmail(split1[1]);
        orf.setDesignation(split1[2]);
        orf.setBirthday(split1[3]);

        saveRecipient(orf.toString());
    }

    private void savePersonalRecipient(@NonNull String[] split1) {
        PersonalRecipientController prc = new PersonalRecipientController();
        Personal_Recipient pr = (Personal_Recipient) prc.create();
        pr.setName(split1[0]);
        pr.setNickname(split1[1]);
        pr.setEmail(split1[2]);
        pr.setBirthday(split1[3]);

        saveRecipient(pr.toString());
    }

    private void addNewCustomer1() {
        printInstructions("Enter 1: if you want to add a new recipient\n" +
                "Enter 2: If you want to get a specified recipient by email\n" +
                "Enter 3: If you want to get all the recipients");
        int input = giveUserSelectedOption(0);

        switch (input) {
            case 1:
                String[] split = new String[0];
                String[] split1 = new String[0];
                printInstructions("\n" +
                        "input format - \n" +
                        "Official: Nimal,nimal@gmail.com,ceo\n" +
                        "Office_friend: kamal,kamal@gmail.com,clerk,2000/09/08\n" +
                        "Personal: sunil,sunil@gmail.com,<nick-name>,2000/08/03");

                @Nullable
                String recipientDetails = giveUserInsertedDetails();

                if (recipientDetails == null) {
                    System.out.println("Input was null. Please try again!");
                    return;
                }

                try {
                    split = recipientDetails.split(": ");
                    split1 = split[1].split(",");
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Please Enter according to the correct format!");
                }

                switch (split[0]) {
                    case ("Official"):
                        saveOfficialRecipient(split1);
                        break;
                    case ("Office_friend"):
                        saveOfficialFriendRecipient(split1);
                        break;
                    case ("Personal"):
                        savePersonalRecipient(split1);
                        break;
                    default:
                        printInstructions("Please follow the given format for input!");
                        break;
                }

                break;
            case 2:
                System.out.print("Please enter the email of the recipient: ");
                try {
                    System.out.println(fileService.findRecipientByEmailAddress(reader.readLine()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                @Nullable String[] recipients = fileService.getAllRecipients();
                if (recipients == null) {
                    System.out.println("No recipients found!");
                    return;
                }
                for (String recipient : recipients) {
                    if (recipient == null) continue;
                    System.out.println(recipient);
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
        String[] emailDetails;
        printInstructions("Please input your sending email details!\n" +
                "Input format: recipient's email, subject, content");

        @Nullable
        String userInput = giveUserInsertedDetails();
        if (userInput == null) {
            System.out.println("Input is null. Aborting.");
            return;
        }

        emailDetails = userInput.split(", ");
        if (emailDetails.length < 3) {
            System.out.println("Invalid input. Aborting.");
            return;
        }

        BasicEmailController bec = new BasicEmailController();
        Email email = bec.create();
        email.setRecipient(emailDetails[0]);
        email.setSubject(emailDetails[1]);
        email.setContent(emailDetails[2]);
        email.setSendingDate(dtf.format(now));
        if (emailService.sendMail(email)) {
            System.out.println("Your email was sent successfully!");
            boolean b = fileService.saveEmail(email);
            System.out.println(b ? "Mail saved in the server." : "Error occurred saving the mail.");
        }
    }

    private void printRecipientsDueToBirthday3() {
        System.out.println("Please enter the birthday of the recipients: ");
        System.out.println("input format - yyyy/MM/dd (ex: 2018/09/17)");
        @MonotonicNonNull
        ArrayList<String> recipients = new ArrayList<>();
        try {
            @Nullable String bodInput = reader.readLine();
            if (bodInput != null) {
                recipients = fileService.findRecipientsByBOD(bodInput);
            } else {
                System.out.println("Input was null. Cannot search for recipients.");
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

        System.out.print("Please enter the birthday of the sent emails: ");
        System.out.println("input format - yyyy/MM/dd (ex: 2018/09/17)");
        ArrayList<Email> emails;
        try {
            @Nullable
            String input = reader.readLine();
            if (input == null) {
                System.out.println("Invalid input");
                return;
            }
            emails = fileService.findMail(input);
            emails.forEach(System.out::println);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void giveRecipientCount5() {
        @Nullable String[] recipientArrayNullable = fileService.getAllRecipients();
        if (recipientArrayNullable == null) {
            System.out.println("No recipients found.");
            return;
        }
    
        @SuppressWarnings("nullness")
        String[] recipients = recipientArrayNullable;
    
        System.out.println(recipients.length);
    }    

    private void shutdownSystem6() {
        System.out.println("System Shutdown!");
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private int giveUserSelectedOption(int option) {
        try {
            @Nullable String input = reader.readLine();
            if (input != null) {
                option = Integer.parseInt(input);
            } else {
                System.out.println("Input was null. Defaulting to option " + option);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Please enter a valid integer value!");
        }
        return option;
    }
    
    private @Nullable String giveUserInsertedDetails() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("Your input operation is failed or interrupted!");
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
                    + "5 - Printing out the number of recipient objects in the application\n"
                    + "6 - exit\n");
            selectOptions(giveUserSelectedOption(6));
        }
    }

    private void handleGlobalExceptions() {
        Handler1 handler = new Handler1();
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }

    public static void main(@NonNull String[] args) {
        EmailClient emailClient = new EmailClient();

        emailClient.handleGlobalExceptions();
        emailClient.greetRecipients();
        emailClient.actionController();
    }
}