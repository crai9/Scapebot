package com.craiig.scapebot.listeners;

import com.craiig.scapebot.commands.Commands;
import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.chat.messages.ChatMessage;
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

            System.out.println("Message: '" + e.getMessage().getContent() + "' sent by " + e.getMessage().getSender().getDisplayName());

            if(e.getMessage().getContent().asPlaintext().startsWith("!") && e.getMessage().getContent().asPlaintext().length() > 1){

                String command = e.getMessage().getContent().asPlaintext().trim().substring(1).split(" ")[0].toLowerCase();

                switchCommand(command, e.getMessage());
            }

        } catch (ConnectionException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private void switchCommand(String command, ChatMessage message) throws ConnectionException{

        switch (command){

            case "hey":
                commands.hey(message);
                break;

            case "shutdown":
                commands.shutdown(message);
                break;

            default:
                System.out.println("Not a recognised command");
                break;
        }

    }

}
