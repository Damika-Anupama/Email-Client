package com.damika.emailclient.factory.implementations;

import com.damika.emailclient.factory.EmailController;
import com.damika.emailclient.factory.NewEmailCreator;
import org.checkerframework.checker.nullness.qual.NonNull;

public class BasicEmailController extends EmailController {
    @Override
    public @NonNull NewEmailCreator giveEmailObject() {
        return new EmailCreator();
    }
}