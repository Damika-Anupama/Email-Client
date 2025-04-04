package com.damika.emailclient.factory.implementations;

import com.damika.emailclient.factory.NewEmailCreator;
import com.damika.emailclient.model.Email;

public class EmailCreator implements NewEmailCreator {
    @Override
    public Email create() {
        return new Email();
    }
}
