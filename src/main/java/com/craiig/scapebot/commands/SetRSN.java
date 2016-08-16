package com.craiig.scapebot.commands;

import com.craiig.scapebot.utilities.FileUtilities;
import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Craig on 15/03/2016, 01:18.
 */
public class SetRSN extends Command {

    @Override
    public String getName() {
        return "setrsn";
    }

    public String[] getAliases(){
        return new String[] {};
    }

    @Override
    public void run(ChatMessage msg, List<Command> commands, String trigger) throws ConnectionException {

        String rsn = msg.getContent().asPlaintext().replace("!setrsn", "").trim();

        if(rsn.contains(",") || rsn.length() < 1){
            msg.getChat().sendMessage("Invalid RSN");
            return;
        }

        ArrayList<String> existing = FileUtilities.readTextFile("data/rsn.txt");
        HashMap<String, String> pairs = new HashMap();

        for(String pair : existing){

            String[] split = pair.split(",");
            pairs.put(split[0], split[1]);

        }

        pairs.remove(msg.getSender().getUsername());
        pairs.put(msg.getSender().getUsername(), rsn);

        FileUtilities.emptyFile("data/rsn.txt");

        for(Map.Entry<String,String> entry : pairs.entrySet()){
            FileUtilities.writeToTextFile("data/", "rsn.txt", entry.getKey() + "," + entry.getValue());
        }

        msg.getChat().sendMessage(msg.getSender().getUsername() + " is now known as: " + rsn);
    }
}
