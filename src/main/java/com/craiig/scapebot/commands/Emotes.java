package com.craiig.scapebot.commands;

import com.craiig.scapebot.utilities.DataSets;
import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static com.craiig.scapebot.utilities.CommonUtilities.participantToUser;


public class Emotes extends Command {

    public String getName(){
        return "emotes";
    }

    public String[] getAliases(){

        return DataSets.emotes().keySet().toArray(new String[DataSets.emotes().size()]);

    }

    public void run(ChatMessage msg, List<Command> commands, String trigger) throws ConnectionException {

        if(trigger.equals(getName())){

            String commandList = "";
            for (String name: getAliases()) {
                commandList += "!" + name + ", ";
            }
            commandList = commandList.substring(0, commandList.length()-2);

            msg.getChat().sendMessage("To use an emote, try the following commands: " + commandList + " (not case sensitive)");
            return;
        }

        HashMap<String, String> pairs = DataSets.emotes();

        if(pairs.containsKey(trigger)){

            String path = pairs.get(trigger);

            File emote = new File(path);

            try {
                msg.getChat().sendImage(emote);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
