package com.craiig.scapebot.commands;

import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.participants.User;

import java.util.List;

import static com.craiig.scapebot.utilities.CommonUtilities.log;
import static com.craiig.scapebot.utilities.CommonUtilities.participantToUser;


public class Shutdown extends Command {

    public String getName() {
        return "shutdown";
    }

    public String[] getAliases(){
        return new String[] {"quit", "exit"};
    }

    public void run(ChatMessage msg, List<Command> commands, String trigger) throws ConnectionException {

        if(participantToUser(msg.getSender()).getUsername().equals("theoptimisticcow")){
            msg.getChat().sendMessage("Shutting down");
            log("Logging out");
            msg.getClient().logout();
        } else {
            msg.getChat().sendMessage("Admin only command.");
        }

    }
}
