package com.damika.emailclient.factory;

import com.damika.emailclient.model.Email;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class EmailController {
    public abstract @NonNull NewEmailCreator giveEmailObject();

    public @NonNull Email create() {
        @NonNull
        NewEmailCreator creator = giveEmailObject();
        return creator.create();
    }
}