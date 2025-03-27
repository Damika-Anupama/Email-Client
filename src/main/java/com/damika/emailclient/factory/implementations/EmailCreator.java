package com.damika.emailclient.factory.implementations;

import com.damika.emailclient.factory.NewEmailCreator;
import com.damika.emailclient.model.Email;
import org.checkerframework.checker.nullness.qual.NonNull;

public class EmailCreator implements NewEmailCreator {
    @Override
    public @NonNull Email create() {
        return new Email();
    }
}

