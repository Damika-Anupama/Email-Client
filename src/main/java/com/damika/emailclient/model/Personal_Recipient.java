package com.damika.emailclient.model;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

public class Personal_Recipient extends Recipient {
    private @MonotonicNonNull String nickname;
    private @MonotonicNonNull String birthday;

    public Personal_Recipient() {
    }

    @EnsuresNonNull({ "this.name", "this.email", "this.nickname", "this.birthday" })
    public void initialize(@NonNull String name, @NonNull String email, @NonNull String nickname,
            @NonNull String birthday) {
        super.initialize(name, email);
        this.nickname = nickname;
        this.birthday = birthday;
    }

    @RequiresNonNull("this.nickname")
    public @NonNull String getNickname() {
        return nickname;
    }

    @EnsuresNonNull("this.nickname")
    public void setNickname(@NonNull String nickname) {
        this.nickname = nickname;
    }

    @RequiresNonNull("this.birthday")
    public @NonNull String getBirthday() {
        return birthday;
    }

    @EnsuresNonNull("this.birthday")
    public void setBirthday(@NonNull String birthday) {
        this.birthday = birthday;
    }

    @Override
    @SuppressWarnings("nullness")
    public String toString() {
        return "Personal: " +
                getName() + ',' +
                nickname + ',' +
                getEmail() + ',' +
                birthday;
    }
}
