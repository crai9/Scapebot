package com.craiig.scapebot.commands;

import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Craig on 14/03/2016, 01:14.
 */
public class Commands extends Command {

    public String getName() {
        return "commands";
    }

    public String[] getAliases(){
        return new String[] {"help", "command", "info"};
    }

    public void run(ChatMessage msg, List<Command> commands, String trigger) throws ConnectionException {


            String response = "Available commands are: ";

            for(Command cmd: commands){
                if(!cmd.getName().equals("")){
                    response += cmd.getName() + ", ";
                }
            }

            response = response.substring(0, response.length()-2) + ".";
            msg.getChat().sendMessage(response);

            return;


    }
}
