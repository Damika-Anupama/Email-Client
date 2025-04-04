package com.damika.emailclient.model;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;

import org.checkerframework.checker.nullness.qual.RequiresNonNull;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

public class Personal_Recipient extends Recipient {
    private @MonotonicNonNull String nickname;
    private @MonotonicNonNull String birthday;

    public Personal_Recipient() {
    }

    @EnsuresNonNull({ "this.name", "this.email", "this.nickname", "this.birthday" })
    public void initialize(String name, String email, String nickname,
            String birthday) {
        super.initialize(name, email);
        this.nickname = nickname;
        this.birthday = birthday;
    }

    @RequiresNonNull("this.nickname")
    public String getNickname() {
        return nickname;
    }

    @EnsuresNonNull("this.nickname")
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @RequiresNonNull("this.birthday")
    public String getBirthday() {
        return birthday;
    }

    @EnsuresNonNull("this.birthday")
    public void setBirthday(String birthday) {
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
