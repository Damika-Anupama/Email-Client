package com.damika.emailclient.model;

public class Official_Recipient extends Recipient {
    private String designation;

    public Official_Recipient() {
    }

    public void initialize(String name, String email, String designation) {
        super.initialize(name, email);
        this.designation = designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDesignation() {
        return designation;
    }

    @Override
    public String toString() {
        return "Official: " +
                getName() + ',' +
                getEmail() + ',' +
                designation;
    }
}
