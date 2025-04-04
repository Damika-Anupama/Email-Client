package com.damika.emailclient.model;

public class Official_Recipient_Friend extends Official_Recipient {
    private String birthday;

    public Official_Recipient_Friend() {
    }

    public void initialize(String name, String email, String designation,
            String birthday) {
        super.initialize(name, email, designation);
        this.birthday = birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthday() {
        return birthday;
    }

    @Override
    public String toString() {
        return "Office_friend: " +
                getName() + ',' +
                getEmail() + ',' +
                getDesignation() + ',' +
                birthday;
    }
}
