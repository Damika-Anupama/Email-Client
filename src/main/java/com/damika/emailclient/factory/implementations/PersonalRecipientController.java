package com.damika.emailclient.factory.implementations;

import com.damika.emailclient.factory.RecipientController;
import com.damika.emailclient.factory.NewRecipientCreator;

public class PersonalRecipientController extends RecipientController {
    @Override
    public NewRecipientCreator giveRecipientObject() {
        return new PersonalRecipientCreator();
    }
}
