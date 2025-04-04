package com.damika.emailclient.factory.implementations;

import com.damika.emailclient.factory.EmailController;
import com.damika.emailclient.factory.NewEmailCreator;

public class BasicEmailController extends EmailController {
    @Override
    public NewEmailCreator giveEmailObject() {
        return new EmailCreator();
    }
}