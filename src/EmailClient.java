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

    public Recipient(String name, String email) {
        this.name = name;
        this.email = email;
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

    public Official_Recipient(String designation) {
        this.designation = designation;
    }

    public Official_Recipient(String name, String email, String designation) {
        super(name, email);
        this.designation = designation;
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

    public Official_Recipient_Friend(String birthday) {
        this.birthday = birthday;
    }

    public Official_Recipient_Friend(String name, String email, String designation, String birthday) {
        super(name, email, designation);
        this.birthday = birthday;
    }

    public String getBirthday() {
        return birthday;
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

    public Personal_Recipient() {
    }

    public Personal_Recipient(String nickname, String birthday) {
        this.nickname = nickname;
        this.birthday = birthday;
    }

    public Personal_Recipient(String name, String email, String nickname, String birthday) {
        super(name, email);
        this.nickname = nickname;
        this.birthday = birthday;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirthday() {
        return birthday;
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
                new EmailSendingService().sendMail(email);
                try {
                    file_service.saveEmail(email);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

public class EmailClient {
    private final FileService fileService;
    private final EmailSendingService emailService;
    private final BufferedReader reader;

    public EmailClient() {
        this.fileService = fileServiceProvider();
        this.emailService = emailServiceProvider();
        this.reader = bufferedReaderProvider();
    }

    private void greetRecipients() {
        new Thread(new EmailSendingService(), "birthdayWisher").start();
    }

    private BufferedReader bufferedReaderProvider() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    private EmailSendingService emailServiceProvider() {
        return new EmailSendingService();
    }

    private FileService fileServiceProvider() {
        return new FileService();
    }

    private void printEnterOptionTypeMessage() {
        System.out.println("Enter option type: \n"
                + "1 - Adding a new recipient\n"
                + "2 - Sending an email\n"
                + "3 - Printing out all the recipients who have birthdays\n"
                + "4 - Printing out details of all the emails sent\n"
                + "5 - Printing out the number of recipient objects in the application\n"
                + "6 - exit\n"
        );
    }

    private void addNewCustomer1() {
        System.out.println(
                "Enter 1: if you want to add a new recipient\n" +
                        "Enter 2: If you want to get a specified recipient by email\n" +
                        "Enter 3: If you want to get all the recipients"
        );
        int input = 0;
        try {
            input = Integer.parseInt(reader.readLine());
        } catch (Exception e) {
            System.out.println("Error! Please enter a valid input!");
        }
        switch (input) {
            case 1:
                String[] split = new String[0];
                String[] split1 = new String[0];
                System.out.println("\n" +
                        "input format - \n" +
                        "Official: nimal,nimal@gmail.com,ceo\n" +
                        "Office_friend: kamal,kamal@gmail.com,clerk,2000/09/08\n" +
                        "Personal: sunil,<nick-name>,sunil@gmail.com,2000/08/03"
                );
                String recipientDetails = null;
                try {
                    recipientDetails = reader.readLine();
                } catch (IOException e) {
                    System.out.println("Your input operation is failed or interpreted!");
                }
                try {
                    split = recipientDetails.split(": ");
                    split1 = split[1].split(",");
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Please Enter according to the correct format!");
                }
                Official_Recipient official_recipient;
                Official_Recipient_Friend official_recipient_friend;
                Personal_Recipient personal_Recipient;
                switch (split[0]) {
                    case ("Official"):
                        official_recipient = new Official_Recipient(
                                split1[0],
                                split1[1],
                                split1[2]
                        );
                        System.out.println(
                                fileService.saveRecipient(official_recipient.toString()) ?
                                        "Client saved successfully!" :
                                        "Something went wrong!"
                        );
                        break;
                    case ("Office_friend"):
                        official_recipient_friend = new Official_Recipient_Friend(
                                split1[0],
                                split1[1],
                                split1[2],
                                split1[3]
                        );
                        System.out.println(
                                fileService.saveRecipient(official_recipient_friend.toString()) ?
                                        "Client saved successfully!" :
                                        "Something went wrong!"
                        );
                        break;
                    case ("Personal"):
                        personal_Recipient = new Personal_Recipient(
                                split1[0],
                                split1[1],
                                split1[2],
                                split1[3]
                        );
                        System.out.println(
                                fileService.saveRecipient(personal_Recipient.toString()) ?
                                        "Client saved successfully!" :
                                        "Something went wrong!"
                        );
                        break;
                    default:
                        System.out.println("Please follow the given format for input!");
                        break;
                }

                break;
            case 2:
                System.out.print("Please enter the email of the recipient: ");
                System.out.println(fileService.findRecipientByEmailAddress(reader.readLine()));
                break;
            case 3:

                for (String recipient : fileService.getAllRecipients())
                    System.out.println(recipient);
                System.out.println();
                break;
            default:
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
            Email email = new Email(emailDetails[0], emailDetails[1], emailDetails[2], dtf.format(now));

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
        ArrayList<String> recipients = fileService.findRecipientsByBOD(reader.readLine());
        if (recipients.size() == 0) {
            System.out.println("No recipient was found, according to the given birthday!");
        } else {
            recipients.forEach(System.out::println);
        }
    }

    private void printEmails4() {
        System.out.print("Please enter the birthday of the sent emails: ");
        System.out.println("input format - yyyy/MM/dd (ex: 2018/09/17)");
        ArrayList<Email> emails = fileService.findMail(reader.readLine());
        emails.forEach(System.out::println);
    }

    private void giveRecipientCount5() {
        System.out.println(fileService.getAllRecipients().length);
    }

    private void systemShutdown6() {
        System.out.println("System Shutdown!");
        System.exit(0);
    }

    private int giveUserSelectedOption() {
        int option = 6;
        try {
            option = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            System.out.println("Please enter a valid integer value!");
        }
        return option;
    }

    private void actionController() throws IOException, ClassNotFoundException {
        while (true) {
            printEnterOptionTypeMessage();
            switch (giveUserSelectedOption()) {
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
                    systemShutdown6();
                default:
                    System.out.println("Please enter a relevant number to proceed !");
                    break;

            }
        }
    }

    public static void main(String[] args) {
        EmailClient emailClient = new EmailClient();

        emailClient.greetRecipients();
        try {
            emailClient.actionController();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}