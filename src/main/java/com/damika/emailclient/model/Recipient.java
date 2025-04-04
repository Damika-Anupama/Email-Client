package com.damika.emailclient.model;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;

import org.checkerframework.checker.nullness.qual.RequiresNonNull;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

public class Recipient {
    private @MonotonicNonNull String name;
    private @MonotonicNonNull String email;

    public Recipient() {
    }

    @EnsuresNonNull({ "this.name", "this.email" })
    public void initialize(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @RequiresNonNull("this.name")
    public String getName() {
        return name;
    }

    @EnsuresNonNull("this.name")
    public void setName(String name) {
        this.name = name;
    }

    @RequiresNonNull("this.email")
    public String getEmail() {
        return email;
    }

    @EnsuresNonNull("this.email")
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Recipient{name='" + name + "', email='" + email + "'}";
    }
}
