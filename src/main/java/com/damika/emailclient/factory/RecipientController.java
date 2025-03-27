package com.damika.emailclient.factory;

import com.damika.emailclient.model.Recipient;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class RecipientController {
    public abstract @NonNull NewRecipientCreator giveRecipientObject();

    public @NonNull Recipient create() {
        @NonNull
        NewRecipientCreator creator = giveRecipientObject();
        return creator.create();
    }
}
