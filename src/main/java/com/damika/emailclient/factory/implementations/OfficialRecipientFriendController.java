package com.damika.emailclient.factory.implementations;

import com.damika.emailclient.factory.RecipientController;
import com.damika.emailclient.factory.NewRecipientCreator;

public class OfficialRecipientFriendController extends RecipientController {
    @Override
    public NewRecipientCreator giveRecipientObject() {
        return new OfficialRecipientFriendCreator();
    }
}