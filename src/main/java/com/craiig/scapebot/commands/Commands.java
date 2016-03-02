package com.craiig.scapebot.commands;

import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;

/**
 * Created by Craig on 02/03/2016, 00:41, 01:17.

 */
public class Commands {

    private final Skype skype;

    public Commands(Skype skype){
        this.skype = skype;
    }

    public void hey(ChatMessage message) throws ConnectionException{
        message.getChat().sendMessage("Hey, " + message.getSender().getDisplayName());
    }

    public void shutdown(ChatMessage message) throws ConnectionException{
        message.getChat().sendMessage("Shutting down bot");
        skype.logout();
    }


}
