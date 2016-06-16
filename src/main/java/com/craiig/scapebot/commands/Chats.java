package com.craiig.scapebot.commands;

import com.samczsun.skype4j.chat.Chat;
import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Chats extends Command {

    public String getName(){
        return "chats";
    }

    public void run(ChatMessage msg, List<Command> commands) throws ConnectionException {

        String message = "In: " + System.lineSeparator();

        List<Chat> chats = new ArrayList<>(msg.getClient().loadMoreChats(1));

        for(Chat chat : chats){
            message += chat.getIdentity() + System.lineSeparator();
        }

        msg.getChat().sendMessage(Message.fromHtml(message));

    }
}
