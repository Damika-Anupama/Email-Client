package com.damika.emailclient.model;

public class Personal_Recipient extends Recipient {
    private String nickname;
    private String birthday;

    public Personal_Recipient() {
    }

    public void initialize(String name, String email, String nickname,
            String birthday) {
        super.initialize(name, email);
        this.nickname = nickname;
        this.birthday = birthday;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Personal: " +
                getName() + ',' +
                nickname + ',' +
                getEmail() + ',' +
                birthday;
    }
}
