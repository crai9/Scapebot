package com.craiig.scapebot.commands;

import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Craig on 09/03/2016, 01:50.
 */
public class Emotes extends Command {

    public String getName(){
        return "emotes";
    }

    public String[] getAliases(){
        return new String[] {"Kappa", "PJSalt", "FeelsGoodMan", "FeelsBadMan"};
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

        HashMap<String, String> pairs = new HashMap();

        pairs.put("kappa", "img/kappa.png");
        pairs.put("pjsalt", "img/pjsalt.png");
        pairs.put("feelsgoodman", "img/feelsgoodman.png");
        pairs.put("feelsbadman", "img/feelsbadman.png");

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
