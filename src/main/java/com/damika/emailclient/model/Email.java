package com.damika.emailclient.model;

import java.io.Serializable;

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

    public Email(String recipient, String subject, String content,
            String sendingDate) {
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
        return "recipient=" + recipient +
                ", subject=" + subject +
                ", content=" + content +
                ", sentDateTime=" + sendingDate;
    }
}
