package com.damika.emailclient.model;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

public class Official_Recipient extends Recipient {
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
        return "Official: " +
                getName() + ',' +
                getEmail() + ',' +
                designation;
    }
}
