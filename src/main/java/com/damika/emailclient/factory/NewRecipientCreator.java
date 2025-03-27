package com.damika.emailclient.factory;

import com.damika.emailclient.model.Recipient;

public interface NewRecipientCreator {
    Recipient create();
}