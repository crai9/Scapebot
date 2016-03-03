package com.craiig.scapebot.listeners;

import com.craiig.scapebot.commands.Commands;
import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.events.EventHandler;
import com.samczsun.skype4j.events.Listener;
import com.samczsun.skype4j.events.chat.message.MessageEvent;
import com.samczsun.skype4j.exceptions.ConnectionException;

/**
 * Created by Craig on 01/03/2016, 21:05, 01:16.

 */

public class ChatListener implements Listener {

    private final Skype skype;
    private final Commands commands;

    public ChatListener(Skype skype){
        this.skype = skype;
        this.commands = new Commands(this.skype);
    }

    @EventHandler
    public void onMessage(MessageEvent e) {

        try {

            //Store words to search in a text file (phrase, chat, response)
            //Load words into a data structure
            //loop over each word for that chat, in the given message
            //if it contains a word, send the response

            //how to deal with responses being updated dynamically?
            //how to add new words and reload list?



            System.out.println("Message: '" + e.getMessage().getContent() + "' sent by " + e.getMessage().getSender().getDisplayName() + " in (" + e.getMessage().getSender().getChat().getIdentity() + ")");

            if(!e.getMessage().getContent().asPlaintext().startsWith("!") && !e.getMessage().getSender().getUsername().equals(skype.getUsername())){

                if(e.getMessage().getContent().asPlaintext().contains("lols")){
                    e.getChat().sendMessage("You sent a message containing -> 'lols'");
                }

            }

        } catch (ConnectionException ex) {
            System.out.println(ex.getMessage());
        }

    }


}
