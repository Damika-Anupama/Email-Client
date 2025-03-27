package com.damika.emailclient.factory.implementations;

import com.damika.emailclient.factory.RecipientController;
import com.damika.emailclient.factory.NewRecipientCreator;
import org.checkerframework.checker.nullness.qual.NonNull;

public class PersonalRecipientController extends RecipientController {
    @Override
    public @NonNull NewRecipientCreator giveRecipientObject() {
        return new PersonalRecipientCreator();
    }
}
