package com.damika.emailclient.factory;

import com.damika.emailclient.model.Email;

public abstract class EmailController {
    public abstract NewEmailCreator giveEmailObject();

    public Email create() {

        NewEmailCreator creator = giveEmailObject();
        return creator.create();
    }
}