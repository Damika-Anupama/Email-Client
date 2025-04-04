package com.damika.emailclient.model;

import java.io.Serializable;

import org.checkerframework.checker.nullness.qual.RequiresNonNull;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

public class Email implements Serializable {
    private String recipient;
    private String subject;
    private String content;
    private String sendingDate;

    public Email() {
        this.recipient = "";
        this.subject = "";
        this.content = "";
        this.sendingDate = "";
    }

    @EnsuresNonNull({ "this.recipient", "this.subject", "this.content", "this.sendingDate" })
    public Email(String recipient, String subject, String content,
            String sendingDate) {
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
        this.sendingDate = sendingDate;
    }

    @RequiresNonNull("this.recipient")
    public String getRecipient() {
        return recipient;
    }

    @EnsuresNonNull("this.recipient")
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    @RequiresNonNull("this.subject")
    public String getSubject() {
        return subject;
    }

    @EnsuresNonNull("this.subject")
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @RequiresNonNull("this.content")
    public String getContent() {
        return content;
    }

    @EnsuresNonNull("this.content")
    public void setContent(String content) {
        this.content = content;
    }

    @RequiresNonNull("this.sendingDate")
    public String getSendingDate() {
        return sendingDate;
    }

    @EnsuresNonNull("this.sendingDate")
    public void setSendingDate(String sendingDate) {
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
