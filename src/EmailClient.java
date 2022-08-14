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

/*Model classes*/
class Recipient {
    private String name;
    private String email;

    public Recipient() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
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
    private String designation;

    public Official_Recipient() {
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
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
    private String birthday;

    public Official_Recipient_Friend() {
    }

    public void setBirthday(String birthday) {
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
    private String nickname;
    private String birthday;


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public void setBirthday(String birthday) {
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
    private String recipient;
    private String subject;
    private String content;
    private String sendingDate;

    public Email() {
    }

    public Email(String recipient, String subject, String content, String sendingDate) {
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
        this.sendingDate = sendingDate;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendingDate() {
        return sendingDate;
    }

    public void setSendingDate(String sendingDate) {
        this.sendingDate = sendingDate;
    }

    @Override
    public String toString() {
        return
                "recipient=" + recipient +
                        ", subject=" + subject +
                        ", content=" + content +
                        ", sentDateTime=" + sendingDate;
    }
}

/*Apply Factory Method DP for model class creation*/
interface NewRecipientCreator {
    Recipient create();
}

interface NewEmailCreator {
    Email create();
}

class OfficialRecipientCreator implements NewRecipientCreator {

    @Override
    public Recipient create() {
        return new Official_Recipient();
    }
}

class OfficialRecipientFriendCreator implements NewRecipientCreator {
    @Override
    public Recipient create() {
        return new Official_Recipient_Friend();
    }
}

class PersonalRecipientCreator implements NewRecipientCreator {

    @Override
    public Recipient create() {
        return new Personal_Recipient();
    }
}

class EmailCreator implements NewEmailCreator {

    @Override
    public Email create() {
        return new Email();
    }
}

abstract class RecipientController {
    public abstract NewRecipientCreator giveRecipientObject();

    public Recipient create() {
        NewRecipientCreator creator = giveRecipientObject();
        return creator.create();
    }
}

abstract class EmailController {
    public abstract NewEmailCreator giveEmailObject();

    public Email create() {
        NewEmailCreator creator = giveEmailObject();
        return creator.create();
    }
}

class OfficialRecipientController extends RecipientController {

    @Override
    public OfficialRecipientCreator giveRecipientObject() {
        return new OfficialRecipientCreator();
    }
}

class OfficialRecipientFriendController extends RecipientController {

    @Override
    public NewRecipientCreator giveRecipientObject() {
        return new OfficialRecipientFriendCreator();
    }
}

class PersonalRecipientController extends RecipientController {

    @Override
    public NewRecipientCreator giveRecipientObject() {
        return new PersonalRecipientCreator();
    }
}

class BasicEmailController extends EmailController {

    @Override
    public NewEmailCreator giveEmailObject() {
        return new EmailCreator();
    }
}

/*File Service*/
class FileService {
    // path can be absolute or relative.
    final static Path clientList = Paths.get("src/Asset/ClientList.txt");
    final static Path emailList = Paths.get("src/Asset/EmailList.txt");

    /* Recipient Service <- uses IO(java nio package)*/
    public boolean saveRecipient(String recipient) {
        if (
                Files.exists(clientList) &&
                        findRecipientByEmailAddress(recipient.split(": ")[1].split(",")[1]) != null
        ) {
            // check whether the client exists (by user email) in the file, then
            System.out.println("This user already exists!");
            return false;
        }

        /* If the clientList.txt file doesn't exist create it and append to it. */
        try (BufferedWriter writer = Files.newBufferedWriter(
                clientList,
                StandardCharsets.UTF_8,
                StandardOpenOption.APPEND,
                StandardOpenOption.CREATE
        )) {
            writer.write("\n" + recipient);
            System.out.println("Successfully inserted the recipient!");
            return true;
        } catch (IOException e) {
            System.out.println("One of relevant files doesn't exists, Please contact the developer!");
            return false;
        }
    }

    public String[] getAllRecipients() {
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
    public String findRecipientByEmailAddress(String email) {
        for (String recipient : getAllRecipients()) {
            String recipientEmail = recipient.split(": ")[1].split(",")[1];
            if (email.equals(recipientEmail)) {
                return recipient;
            }
        }
        return null;
    }

    public ArrayList<String> findRecipientsByBOD(String bod) {
        ArrayList<String> recipients = new ArrayList<>();
        for (String recipient : getAllRecipients()) {
            String[] recipientDetails = recipient.split(": ")[1].split(",");
            if (recipientDetails.length == 4 && recipientDetails[3].equals(bod)) {
                recipients.add(recipient);
            }
        }
        return recipients;
    }

    /*Email Service <- uses serialization*/
    public boolean saveEmail(Email email) throws IOException {
        FileOutputStream fileStream = new FileOutputStream(String.valueOf(emailList));
        // Make a ObjectOutputStream
        ObjectOutputStream os = new ObjectOutputStream(fileStream);
        // Write the object
        os.writeObject(email);

        return true;
    }

    public ArrayList<Email> findMail(String sentDate) throws IOException, ClassNotFoundException {
        ArrayList<Email> emails = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(String.valueOf(emailList));
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Email email;
            while ((email = (Email) ois.readObject()) != null) {
                if (email.getSendingDate().equals(sentDate)) {
                    emails.add(email);
                }
            }
        } catch (EOFException e) {//bad practise
        }


        return emails;
    }
}

/*Email Sending Service*/
class EmailSendingService implements Runnable {
    public boolean sendMail(Email email) {

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
            message.setFrom(new InternetAddress(sender));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getRecipient()));
            message.setSubject(email.getSubject());
            message.setText(email.getContent());

            Transport transport = session.getTransport("smtp");


            transport.connect(host, sender, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            file_service.saveEmail(email);
            return true;
        } catch (MessagingException | IOException ae) {
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
        for (String s : file_service.getAllRecipients()) {
            String[] split1 = s.split(": ");
            String[] split2 = split1[1].split(",");
            if (split2.length == 4 && split2[3].substring(5).equals(today)) {
                String content = null, type = split1[0];
                if (type.equals("Personal")) {
                    content = "hugs and love on your birthday. Damika";
                } else if (type.equals("Office_friend")) {
                    content = "Wish you a Happy Birthday. Damika";
                }
                // send the birthday wish from here (from another method)

                Email email = new Email(split2[1], "Surprise from Damika", content, dtf.format(now));
                sendMail(email);
                try {
                    file_service.saveEmail(email);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

class Handler1 implements Thread.UncaughtExceptionHandler {
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("Throwable: " + e.getMessage());
        System.out.println(t.toString());
    }
}


public class EmailClient {
    private final FileService fileService;
    private final EmailSendingService emailService;
    private final BufferedReader reader;

    public EmailClient() {
        this.fileService = getFileServiceProvider();
        this.emailService = getEmailServiceProvider();
        this.reader = getBufferedReaderProvider();
    }

    private void greetRecipients() {
        new Thread(new EmailSendingService(), "birthdayWisher").start();
    }

    private BufferedReader getBufferedReaderProvider() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    private EmailSendingService getEmailServiceProvider() {
        return new EmailSendingService();
    }

    private FileService getFileServiceProvider() {
        return new FileService();
    }

    private void printInstructions(String instructions) {
        System.out.println(instructions);
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
                        "Official: nimal,nimal@gmail.com,ceo\n" +
                        "Office_friend: kamal,kamal@gmail.com,clerk,2000/09/08\n" +
                        "Personal: sunil,sunil@gmail.com,<nick-name>,2000/08/03");

                String recipientDetails = giveUserInsertedDetails();

                try {
                    split = recipientDetails.split(": ");
                    split1 = split[1].split(",");
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Please Enter according to the correct format!");
                }

                switch (split[0]) {
                    case ("Official"):
                        OfficialRecipientController orc = new OfficialRecipientController();
                        Official_Recipient or = (Official_Recipient) orc.create();

                        or.setName(split1[0]);
                        or.setEmail(split1[1]);
                        or.setDesignation(split1[2]);
                        System.out.println(
                                fileService.saveRecipient(or.toString()) ?
                                        "Client saved successfully!" :
                                        "Try again!"
                        );
                        break;
                    case ("Office_friend"):
                        OfficialRecipientFriendController orfc = new OfficialRecipientFriendController();
                        Official_Recipient_Friend orf = (Official_Recipient_Friend) orfc.create();
                        orf.setName(split1[0]);
                        orf.setEmail(split1[1]);
                        orf.setDesignation(split1[2]);
                        orf.setBirthday(split1[3]);

                        System.out.println(
                                fileService.saveRecipient(orf.toString()) ?
                                        "Client saved successfully!" :
                                        "Try again!"
                        );
                        break;
                    case ("Personal"):
                        PersonalRecipientController prc = new PersonalRecipientController();
                        Personal_Recipient pr = (Personal_Recipient) prc.create();
                        pr.setName(split1[0]);
                        pr.setNickname(split1[1]);
                        pr.setEmail(split1[2]);
                        pr.setBirthday(split1[3]);

                        System.out.println(
                                fileService.saveRecipient(pr.toString()) ?
                                        "Client saved successfully!" :
                                        "Try again!"
                        );
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
                for (String recipient : fileService.getAllRecipients()) {
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
        String[] emailDetails = new String[3];
        System.out.println("Please input your sending email details!");
        System.out.println("Input format: recipient's email, subject, content");
        try {
            emailDetails = reader.readLine().split(", ");
        } catch (Exception e) {
            System.out.println("Please type the email details according to the given format");
        }
        try {
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
        } catch (ArrayIndexOutOfBoundsException | IOException e) {
            System.out.println("Please insert the data according the given format!");
        }
    }

    private void printRecipientsDueToBirthday3() {
        System.out.println("Please enter the birthday of the recipients: ");
        System.out.println("input format - yyyy/MM/dd (ex: 2018/09/17)");
        ArrayList<String> recipients = null;
        try {
            recipients = fileService.findRecipientsByBOD(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
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
            emails = fileService.findMail(reader.readLine());
            emails.forEach(System.out::println);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void giveRecipientCount5() {
        System.out.println(fileService.getAllRecipients().length);
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
            option = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            System.out.println("Please enter a valid integer value!");
        }
        return option;
    }

    private String giveUserInsertedDetails() {
        String details = null;
        try {
            details = reader.readLine();
        } catch (IOException e) {
            System.out.println("Your input operation is failed or interpreted!");
        }
        return details;
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
            default:
                System.out.println("Please enter a relevant number to proceed !");
                break;
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void actionController() {

        while (true) {
            printInstructions("Enter option type: \n"
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

    public static void main(String[] args) {
        EmailClient emailClient = new EmailClient();

        emailClient.handleGlobalExceptions();
        emailClient.greetRecipients();
        emailClient.actionController();
    }
}