package com.damika.emailclient.model;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

public class Official_Recipient_Friend extends Official_Recipient {
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

