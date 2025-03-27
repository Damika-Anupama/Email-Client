package com.damika.emailclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;

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

import org.checkerframework.checker.nullness.qual.*;

/*Model classes*/
class Recipient {
    private @MonotonicNonNull String name;
    private @MonotonicNonNull String email;

    public Recipient() {
    }

    @EnsuresNonNull({ "this.name", "this.email" })
    public void initialize(@NonNull String name, @NonNull String email) {
        this.name = name;
        this.email = email;
    }

    @RequiresNonNull("this.name")
    public @NonNull String getName() {
        return name;
    }

    @EnsuresNonNull("this.name")
    public void setName(@NonNull String name) {
        this.name = name;
    }

    @RequiresNonNull("this.email")
    public @NonNull String getEmail() {
        return email;
    }

    @EnsuresNonNull("this.email")
    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Recipient{name='" + name + "', email='" + email + "'}";
    }
}

class Official_Recipient extends Recipient {
    private @MonotonicNonNull String designation;

    public Official_Recipient() {
    }

    @EnsuresNonNull("this.designation")
    public void setDesignation(@NonNull String designation) {
        this.designation = designation;
    }

    @RequiresNonNull("this.designation")
    public @NonNull String getDesignation() {
        return designation;
    }

    @Override
    @SuppressWarnings("nullness")
    public String toString() {
        return "Official_Recipient{name='" + getName() + "', email='" + getEmail() + "', designation='" + designation
                + "'}";
    }
}

class Official_Recipient_Friend extends Official_Recipient {
    private @MonotonicNonNull String birthday;

    public Official_Recipient_Friend() {
    }

    @EnsuresNonNull("this.birthday")
    public void setBirthday(@NonNull String birthday) {
        this.birthday = birthday;
    }

    @RequiresNonNull("this.birthday")
    public @NonNull String getBirthday() {
        return birthday;
    }

    @Override
    @SuppressWarnings("nullness")
    public String toString() {
        return "Official_Recipient_Friend{name='" + getName() + "', email='" + getEmail() + "', designation='"
                + getDesignation() + "', birthday='" + birthday + "'}";
    }
}

class Personal_Recipient extends Recipient {
    private @MonotonicNonNull String nickname;
    private @MonotonicNonNull String birthday;

    @EnsuresNonNull({ "this.name", "this.email", "this.nickname", "this.birthday" })
    public void initialize(@NonNull String name, @NonNull String email, @NonNull String nickname,
            @NonNull String birthday) {
        super.initialize(name, email);
        this.nickname = nickname;
        this.birthday = birthday;
    }

    @RequiresNonNull("this.nickname")
    public @NonNull String getNickname() {
        return nickname;
    }

    @EnsuresNonNull("this.nickname")
    public void setNickname(@NonNull String nickname) {
        this.nickname = nickname;
    }

    @RequiresNonNull("this.birthday")
    public @NonNull String getBirthday() {
        return birthday;
    }

    @EnsuresNonNull("this.birthday")
    public void setBirthday(@NonNull String birthday) {
        this.birthday = birthday;
    }

    @Override
    @SuppressWarnings("nullness")
    public String toString() {
        return "Personal_Recipient{name='" + getName() + "', email='" + getEmail() + "', nickname='" + nickname
                + "', birthday='" + birthday + "'}";
    }
}

class Email implements Serializable {
    private @NonNull String recipient;
    private @NonNull String subject;
    private @NonNull String content;
    private @NonNull String sendingDate;

    public Email() {
        this.recipient = "";
        this.subject = "";
        this.content = "";
        this.sendingDate = "";
    }

    @EnsuresNonNull({ "this.recipient", "this.subject", "this.content", "this.sendingDate" })
    public Email(@NonNull String recipient, @NonNull String subject, @NonNull String content,
            @NonNull String sendingDate) {
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
        this.sendingDate = sendingDate;
    }

    @RequiresNonNull("this.recipient")
    public @NonNull String getRecipient() {
        return recipient;
    }

    @EnsuresNonNull("this.recipient")
    public void setRecipient(@NonNull String recipient) {
        this.recipient = recipient;
    }

    @RequiresNonNull("this.subject")
    public @NonNull String getSubject() {
        return subject;
    }

    @EnsuresNonNull("this.subject")
    public void setSubject(@NonNull String subject) {
        this.subject = subject;
    }

    @RequiresNonNull("this.content")
    public @NonNull String getContent() {
        return content;
    }

    @EnsuresNonNull("this.content")
    public void setContent(@NonNull String content) {
        this.content = content;
    }

    @RequiresNonNull("this.sendingDate")
    public @NonNull String getSendingDate() {
        return sendingDate;
    }

    @EnsuresNonNull("this.sendingDate")
    public void setSendingDate(@NonNull String sendingDate) {
        this.sendingDate = sendingDate;
    }

    @Override
    public String toString() {
        return "Email{recipient='" + recipient + "', subject='" + subject + "', content='" + content
                + "', sendingDate='" + sendingDate + "'}";
    }
}

/* Apply Factory Method DP for model class creation */
interface NewRecipientCreator {
    Recipient create();
}

interface NewEmailCreator {
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

/* Custom Object Serialization */
class AppendableObjectOutputStream extends ObjectOutputStream {
    public AppendableObjectOutputStream(@NonNull OutputStream out) throws IOException {
        super(out);
    }

    @Override
    protected void writeStreamHeader() throws IOException {
        // Do NOT write a header — needed for appending
        reset();
    }
}

/* File Service */
class FileService {
    final static @NonNull Path clientList = Paths.get("data/ClientList.txt");
    final static @NonNull Path emailList = Paths.get("data/EmailList.txt");

    /* Recipient Service */
    public boolean saveRecipient(@NonNull String recipient) {
        @Nullable
        String existing = findRecipientByEmailAddress(recipient.split(": ")[1].split(",")[1]);
        if (Files.exists(clientList) && existing != null) {
            System.out.println("This user already exists!");
            return false;
        }

        try (BufferedWriter writer = Files.newBufferedWriter(
                clientList,
                StandardCharsets.UTF_8,
                StandardOpenOption.APPEND,
                StandardOpenOption.CREATE)) {

            if (Files.size(clientList) > 0) {
                writer.write("\n");
            }
            writer.write(recipient);
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
            return recipientFileData.split("\\R");
        } catch (IOException e) {
            System.out.println("There is a failure during reading the recipients, Please contact the developer!");
            return null;
        }
    }

    public @Nullable String findRecipientByEmailAddress(@Nullable String email) {
        @Nullable
        String[] recipients = getAllRecipients();
        if (recipients == null)
            return null;
        for (@Nullable
        String recipient : recipients) {
            if (recipient == null)
                continue;

            String[] parts = recipient.split(": ");
            if (parts.length < 2)
                continue;

            String[] fields = parts[1].split(",");
            if (fields.length < 2)
                continue;

            String recipientEmail = fields[1];
            if (Objects.equals(email, recipientEmail)) {
                return recipient;
            }
        }
        return null;
    }

    public @NonNull ArrayList<@NonNull String> findRecipientsByBOD(@NonNull String bod) {
        ArrayList<@NonNull String> recipients = new ArrayList<>();
        @Nullable
        String[] allRecipients = getAllRecipients();
        if (allRecipients == null)
            return recipients;
        for (@Nullable
        String recipient : allRecipients) {
            if (recipient == null)
                continue;
            String[] recipientDetails = recipient.split(": ")[1].split(",");
            if (recipientDetails.length == 4 && recipientDetails[3].trim().equals(bod.trim())) {
                recipients.add(recipient);
            }
        }
        return recipients;
    }

    public boolean saveEmail(@NonNull Email email) {
        boolean result = false;
        boolean append = Files.exists(emailList);

        try (FileOutputStream fos = new FileOutputStream(String.valueOf(emailList), true);
                ObjectOutputStream oos = append
                        ? new AppendableObjectOutputStream(fos)
                        : new ObjectOutputStream(fos)) {

            oos.writeObject(email);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public @NonNull ArrayList<@NonNull Email> findMail(@NonNull String sentDate) {
        ArrayList<@NonNull Email> emails = new ArrayList<>();

        if (!Files.exists(emailList))
            return emails;

        try (FileInputStream fis = new FileInputStream(String.valueOf(emailList));
                ObjectInputStream ois = new ObjectInputStream(fis)) {

            while (true) {
                try {
                    Object obj = ois.readObject();
                    if (obj instanceof Email) {
                        Email email = (Email) obj;
                        String sendingDate = Objects.requireNonNull(email.getSendingDate(),
                                "Sending date must not be null");
                        if (sentDate.equals(sendingDate)) {
                            emails.add(email);
                        }
                    }
                } catch (EOFException eof) {
                    break; // End of file reached — expected
                }
            }

        } catch (StreamCorruptedException sce) {
            System.err.println("⚠️ EmailList.txt is corrupted or not in valid serialized format.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return emails;
    }
}

/* Email Sending Service */
class EmailSendingService implements Runnable {
    @RequiresNonNull({ "#1.recipient", "#1.subject", "#1.content", "#1.sendingDate" })
    public boolean sendMail(@NonNull Email email) {

        String sender = "palindrome.penguin.unity.clan@gmail.com";
        String password = "hlfmabeyiopoxzov";
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        int port = 587;

        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", host);
        props.put("mail.smtp.user", sender);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            @Nullable
            String recipient = email.getRecipient();
            @Nullable
            String subject = email.getSubject();
            @Nullable
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

            return true;
        } catch (MessagingException ae) {
            ae.printStackTrace();
            return false;
        }
    }

    @Override
    public void run() {
        DateTimeFormatter shortFormat = DateTimeFormatter.ofPattern("MM/dd");
        DateTimeFormatter fullFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String todayShort = shortFormat.format(now);
        String todayFull = fullFormat.format(now);

        FileService file_service = new FileService();
        @Nullable
        String[] recipients = file_service.getAllRecipients();

        if (recipients == null) {
            System.out.println("No recipients to process.");
            return;
        }

        // Load all previously sent emails
        ArrayList<@NonNull Email> previouslySent = file_service.findMail(todayFull);

        for (@Nullable
        String s : recipients) {
            if (s == null)
                continue;

            String[] split1 = s.split(": ");
            if (split1.length < 2) {
                System.err.println("Invalid recipient format: " + s);
                continue;
            }

            String type = split1[0];
            String[] split2 = split1[1].split(",");

            if ((type.equals("Personal") || type.equals("Office_friend")) && split2.length >= 4) {
                String birthday = split2[3]; // yyyy/MM/dd

                if (birthday.length() >= 5 && birthday.substring(5).equals(todayShort)) {
                    String recipientEmail = split2[1];
                    String content = type.equals("Personal")
                            ? "hugs and love on your birthday. Damika"
                            : "Wish you a Happy Birthday. Damika";
                    String subject = "Surprise from Damika";

                    // Check if already sent today
                    boolean alreadySent = previouslySent.stream().anyMatch(mail -> subject.equals(mail.getSubject()) &&
                            recipientEmail.equals(mail.getRecipient()) &&
                            todayFull.equals(mail.getSendingDate()));

                    if (!alreadySent) {
                        Email email = new Email(recipientEmail, subject, content, todayFull);
                        if (sendMail(email)) {
                            System.out.println("🎉 Today is " + split2[0] + "'s birthday! 🎂");
                            System.out.println("Email sent to " + recipientEmail + ":\nSubject: " + subject
                                    + "\nMessage: " + content + "\n");
                            file_service.saveEmail(email); // Save after sending
                        }
                    } else {
                        System.out.println("✅ Birthday email already sent to " + recipientEmail + " today.");
                    }
                }
            }
        }
        System.out.println("✅ Birthday checking complete.");
    }
}

class Handler1 implements Thread.UncaughtExceptionHandler {
    @Override
    @EnsuresNonNull({ "#1", "#2" })
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        System.err.println("Uncaught exception in thread: " + t.getName());
        e.printStackTrace();
    }
}

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
                "Enter 3: If you want to get all the recipients\n");
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

                if (!recipientDetails.contains(": ")) {
                    System.out.println("Invalid format. Missing ': ' separator. Please follow the correct format.");
                    return;
                }

                split = recipientDetails.split(": ");
                if (split.length != 2) {
                    System.out.println("Invalid format. Expected one ':' to separate type and details.");
                    return;
                }

                split1 = split[1].split(",");

                // Validate recipient type and number of fields
                String type = split[0];
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

                if (split1.length != expectedLength) {
                    System.out.println("Invalid number of details for type '" + type + "'. Expected " + expectedLength
                            + " fields.");
                    return;
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
                    @Nullable
                    String email = reader.readLine();
                    if (email != null) {
                        @Nullable
                        String recipient = fileService.findRecipientByEmailAddress(email);
                        if (recipient != null) {
                            System.out.println(recipient);
                        } else {
                            System.out.println("Recipient not found.");
                        }
                    } else {
                        System.out.println("Input was null.");
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
                    if (recipient == null)
                        continue;
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
            @Nullable
            String bodInput = reader.readLine();
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

        System.out.print("Please enter the date when the emails were sent: ");
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
        } catch (IOException e) {
            System.out.println("Error reading input or retrieving email list.");
            e.printStackTrace();
        }
    }

    private void giveRecipientCount5() {
        @Nullable
        String[] recipients = fileService.getAllRecipients();

        if (recipients == null) {
            System.out.println("No recipients found.");
            return;
        }
        System.out.println("Number of recipients: " + recipients.length);
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

    private int giveUserSelectedOption(int defaultOption) {
        while (true) {
            System.out.println("Please enter your option in valid range!");
            try {
                @Nullable
                String input = reader.readLine();
                if (input == null || input.trim().isEmpty()) {
                    System.out.println("Input was empty. Try again.");
                    continue;
                }

                int option = Integer.parseInt(input.trim());
                if (option >= 1 && option <= 6) {
                    return option;
                } else {
                    System.out.println("Please enter a number between 1 and 6.");
                }

            } catch (IOException | NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
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
                    + "5 - Printing out the number of recipients in this application\n"
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