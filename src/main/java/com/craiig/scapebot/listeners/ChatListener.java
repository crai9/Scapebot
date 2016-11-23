package com.craiig.scapebot.listeners;

import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.events.EventHandler;
import com.samczsun.skype4j.events.Listener;
import com.samczsun.skype4j.events.chat.message.MessageEvent;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.exceptions.ConnectionException;

import java.util.Random;

import static com.craiig.scapebot.utilities.CommonUtilities.logMessage;
import static com.craiig.scapebot.utilities.CommonUtilities.participantToUser;

/**
 * Created by Craig on 01/03/2016, 21:05, 01:16.

 */

public class ChatListener implements Listener {

    private final Skype skype;

    public ChatListener(Skype skype){
        this.skype = skype;
    }

    @EventHandler
    public void onMessage(MessageReceivedEvent e) {

        try {

            //Store words to search in a text file (phrase, chat, response)
            //Load words into a data structure
            //loop over each word for that chat, in the given message
            //if it contains a word, send the response

            //how to deal with responses being updated dynamically?
            //how to add new words and reload list?

            logMessage(e.getMessage().getContent().asPlaintext(), participantToUser(e.getMessage().getSender()).getUsername());

            if(!e.getMessage().getContent().asPlaintext().startsWith("!") && !participantToUser(e.getMessage().getSender()).getUsername().equals(skype.getUsername())){

                if(e.getMessage().getContent().asPlaintext().toLowerCase().contains("gz") && !e.getMessage().getContent().asPlaintext().toLowerCase().contains("http") || e.getMessage().getContent().asPlaintext().toLowerCase().contains("grats")){

                    String[] responses = {"Congrats! (rock)", "Gzz! (clap)", "Grats!! (party)"};
                    Random rand = new Random();

                    e.getChat().sendMessage(responses[rand.nextInt((2) + 1)]);
                }

            }

        } catch (ConnectionException ex) {
            System.out.println(ex.getMessage());
        }

    }


}
