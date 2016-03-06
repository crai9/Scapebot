package com.craiig.scapebot.commands;

import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;

/**
 * Created by Craig on 06/03/2016, 23:04.
 */
public class Hey extends Command {

    public String getName(){
        return "hey";
    }

    public void run(ChatMessage msg) throws ConnectionException{
        msg.getChat().sendMessage("Hey, " + msg.getSender().getDisplayName());
    }
}
