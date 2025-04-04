package com.damika.emailclient.factory.implementations;

import com.damika.emailclient.factory.NewRecipientCreator;
import com.damika.emailclient.model.Personal_Recipient;
import com.damika.emailclient.model.Recipient;

public class PersonalRecipientCreator implements NewRecipientCreator {
    @Override
    public Recipient create() {
        return new Personal_Recipient();
    }
}