package com.damika.emailclient.service;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Objects;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import com.damika.emailclient.model.Email;
import com.damika.emailclient.util.AppendableObjectOutputStream;
import com.damika.emailclient.util.IOHandler;

public class FileService {
    private final static @NonNull Path clientList = Paths.get("data/ClientList.txt");
    private final static @NonNull Path emailList = Paths.get("data/EmailList.txt");
    private final @NonNull IOHandler ioHandler;

    static {
        try {
            if (!Files.exists(clientList)) {
                @Nullable
                Path clientListParent = clientList.getParent();
                if (clientListParent != null) {
                    Files.createDirectories(clientListParent);
                }
                Files.createFile(clientList);
            }
            if (!Files.exists(emailList)) {
                @Nullable
                Path emailListParent = emailList.getParent();
                if (emailListParent != null) {
                    Files.createDirectories(emailListParent);
                }
                Files.createFile(emailList);
            }
        } catch (IOException e) {
            System.err.println("Error initializing files: " + e.getMessage());
        }
    }

    public FileService(@NonNull IOHandler ioHandler) {
        this.ioHandler = ioHandler;
    }
    /* Recipient Service */
    public boolean saveRecipient(@NonNull String recipient) {
        // Validate basic format
        String[] parts = recipient.split(": ");
        if (parts.length != 2) {
            ioHandler.printInstructions("Invalid format. Must contain a single ': ' to separate type and details.");
            return false;
        }

        String[] fields = parts[1].split(",");
        if (fields.length < 2) {
            ioHandler.printInstructions("Invalid format. Not enough fields after ': '.");
            return false;
        }

        @Nullable
        String email = fields[1].trim();

        // Check if email already exists
        @Nullable
        String existing = findRecipientByEmailAddress(email);
        if (Files.exists(clientList) && existing != null) {
            ioHandler.printInstructions("This user already exists!");
            return false;
        }

        // Save to file
        try (BufferedWriter writer = Files.newBufferedWriter(
                clientList,
                StandardCharsets.UTF_8,
                StandardOpenOption.APPEND,
                StandardOpenOption.CREATE)) {

            if (Files.size(clientList) > 0) {
                writer.write("\n");
            }
            writer.write(recipient);
            ioHandler.printInstructions("Successfully inserted the recipient!");
            return true;
        } catch (IOException e) {
            ioHandler.printInstructions("File I/O error: " + e.getMessage());
            return false;
        }
    }

    public @Nullable String @Nullable [] getAllRecipients() {
        if (!Files.exists(clientList)) {
            ioHandler.printInstructions("No Recipient exists!");
            return null;
        }
        try {
            byte[] bytes = Files.readAllBytes(clientList);
            String recipientFileData = new String(bytes);
            return recipientFileData.split("\\R");
        } catch (IOException e) {
            ioHandler.printInstructions("There is a failure during reading the recipients, Please contact the developer!");
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
        boolean append = false;
        try {
            append = Files.exists(emailList) && Files.size(emailList) > 0;
        } catch (IOException e) {
            System.err.println("Error checking file size: " + e.getMessage());
        }

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

        if (!Files.exists(emailList)) {
            return emails; // File does not exist, return empty list
        }

        try {
            // Check if the file is empty
            if (Files.size(emailList) == 0) {
                return emails;
            }

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

            }
        } catch (StreamCorruptedException sce) {
            ioHandler.printInstructions(
                    sce.getMessage() + 
                    "\n ⚠️ EmailList.txt is corrupted or not in valid serialized format."
                );
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return emails;
    }
}
