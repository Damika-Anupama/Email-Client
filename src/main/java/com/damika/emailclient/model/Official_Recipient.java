package com.damika.emailclient.model;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;

import org.checkerframework.checker.nullness.qual.RequiresNonNull;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

public class Official_Recipient extends Recipient {
    private @MonotonicNonNull String designation;

    public Official_Recipient() {
    }

    @EnsuresNonNull({ "this.name", "this.email", "this.designation" })
    public void initialize(String name, String email, String designation) {
        super.initialize(name, email);
        this.designation = designation;
    }

    @EnsuresNonNull("this.designation")
    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @RequiresNonNull("this.designation")
    public String getDesignation() {
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
