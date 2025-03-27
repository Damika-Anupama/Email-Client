package com.damika.emailclient.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;
import com.damika.emailclient.model.Email;


public class EmailSendingService implements Runnable {
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
            if (s == null || s.trim().isEmpty()) {
                continue;
            }

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
