package com.damika.emailclient.model;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

public class Recipient {
    private @MonotonicNonNull String name;
    private @MonotonicNonNull String email;

    public Recipient() {
    }

    @EnsuresNonNull({ "this.name", "this.email" })
    public void initialize(@NonNull String name, @NonNull String email) {
        this.name = name;
        this.email = email;
    }

    @RequiresNonNull("this.name")
    public @NonNull String getName() {
        return name;
    }

    @EnsuresNonNull("this.name")
    public void setName(@NonNull String name) {
        this.name = name;
    }

    @RequiresNonNull("this.email")
    public @NonNull String getEmail() {
        return email;
    }

    @EnsuresNonNull("this.email")
    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Recipient{name='" + name + "', email='" + email + "'}";
    }
}

