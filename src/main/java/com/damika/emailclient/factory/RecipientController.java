package com.damika.emailclient.factory;

import com.damika.emailclient.model.Recipient;

public abstract class RecipientController {
    public abstract NewRecipientCreator giveRecipientObject();

    public Recipient create() {

        NewRecipientCreator creator = giveRecipientObject();
        return creator.create();
    }
}
