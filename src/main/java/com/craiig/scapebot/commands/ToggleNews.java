package com.craiig.scapebot.commands;

import com.craiig.scapebot.utilities.FileUtilities;
import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.craiig.scapebot.utilities.CommonUtilities.participantToUser;


public class ToggleNews extends Command {

    @Override
    public String getName() {
        return "togglenews";
    }

    public String[] getAliases(){
        return new String[] { "news" };
    }

    @Override
    public void run(ChatMessage msg, List<Command> commands, String trigger) throws ConnectionException {

        String chatID = msg.getChat().getIdentity();

        ArrayList<String> lines = FileUtilities.readTextFile("data/news-notifications.txt");
        HashMap<String, String> pairs = new HashMap();

        for(String pair : lines){

            String[] split = pair.split(",");
            pairs.put(split[0], split[1]);

        }

        String previousPref = "";
        String preference = "";

        if(pairs.containsKey(chatID)){
            previousPref = pairs.get(chatID);
        } else {
            previousPref = "false";
        }

        if(previousPref.equals("false")){
            preference = "true";
        } else {
            preference = "false";
        }

        pairs.remove(chatID);
        pairs.put(chatID, preference);

        FileUtilities.emptyFile("data/news-notifications.txt");

        for(Map.Entry<String,String> entry : pairs.entrySet()){
            FileUtilities.writeToTextFile("data/", "news-notifications.txt", entry.getKey() + "," + entry.getValue());
        }

        String response = (preference.equals("true")) ? "now" : "no longer";

        msg.getChat().sendMessage("This chat will " + response + " receive RS news notifications.");
    }
}
