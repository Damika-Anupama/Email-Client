package com.damika.emailclient.factory.implementations;

import com.damika.emailclient.factory.NewRecipientCreator;
import com.damika.emailclient.model.Official_Recipient;
import com.damika.emailclient.model.Recipient;

public class OfficialRecipientCreator implements NewRecipientCreator {
    @Override
    public Recipient create() {
        return new Official_Recipient();
    }
}