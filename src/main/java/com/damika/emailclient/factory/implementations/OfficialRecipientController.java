package com.damika.emailclient.factory.implementations;

import com.damika.emailclient.factory.RecipientController;
import com.damika.emailclient.factory.NewRecipientCreator;

public class OfficialRecipientController extends RecipientController {
    @Override
    public NewRecipientCreator giveRecipientObject() {
        return new OfficialRecipientCreator();
    }
}
