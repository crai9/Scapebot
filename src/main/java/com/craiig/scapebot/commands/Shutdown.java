package com.craiig.scapebot.commands;

import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;

import java.util.List;

import static com.craiig.scapebot.utilities.CommonUtilities.log;

/**
 * Created by Craig on 14/03/2016, 00:42.
 */
public class Shutdown extends Command {

    public String getName() {
        return "shutdown";
    }

    public void run(ChatMessage msg, List<Command> commands) throws ConnectionException {

        if(msg.getSender().getUsername().equals("theoptimisticcow")){
            msg.getChat().sendMessage("Shutting down");
            log("Logging out");
            msg.getClient().logout();
        } else {
            msg.getChat().sendMessage("Admin only command.");
        }

    }
}
