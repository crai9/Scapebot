package com.craiig.scapebot.listeners;

import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.events.EventHandler;
import com.samczsun.skype4j.events.Listener;
import com.samczsun.skype4j.events.contact.ContactRequestEvent;
import com.samczsun.skype4j.exceptions.ChatNotFoundException;
import com.samczsun.skype4j.exceptions.ConnectionException;


public class ContactRequestListener implements Listener {

    private final Skype skype;

    public ContactRequestListener(Skype skype){

        this.skype = skype;

    }

    @EventHandler
    public void onContactRequest(ContactRequestEvent e){

        try {

            String username = e.getRequest().getSender().getUsername();
            e.getRequest().accept();
            System.out.println("Accepted FR from: " + username);

            //wait for Skype to catch up
            Thread.sleep(6000L);

            skype.getOrLoadContact(username).getPrivateConversation()
                    .sendMessage("Hello! You may now invite me to any other group chats. " +
                            System.lineSeparator() +
                            "use !commands for more information on available commands");

            skype.loadAllContacts();

        } catch (ConnectionException | ChatNotFoundException | InterruptedException e1) {
            e1.printStackTrace();
        }

    }

}
