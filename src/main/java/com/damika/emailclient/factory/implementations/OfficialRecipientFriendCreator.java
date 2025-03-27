package com.damika.emailclient.factory.implementations;

import com.damika.emailclient.factory.NewRecipientCreator;
import com.damika.emailclient.model.Official_Recipient_Friend;
import com.damika.emailclient.model.Recipient;
import org.checkerframework.checker.nullness.qual.NonNull;

public class OfficialRecipientFriendCreator implements NewRecipientCreator {
    @Override
    public @NonNull Recipient create() {
        return new Official_Recipient_Friend();
    }
}
