package com.damika.emailclient.model;

import java.io.Serializable;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

public class Email implements Serializable {
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
