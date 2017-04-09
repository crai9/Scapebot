package com.craiig.scapebot.commands;

import com.craiig.scapebot.utilities.FileUtilities;
import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;
import com.samczsun.skype4j.formatting.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.craiig.scapebot.utilities.CommonUtilities.participantToUser;


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

        //String rsn = msg.getContent().asPlaintext().replace("!" + trigger, "").trim();
        String rsn = msg.getContent().asPlaintext().toLowerCase().replace("!" + trigger, "").trim();


        if(rsn.contains(",") || rsn.length() < 1){

            msg.getChat().sendMessage(Message.create()
                    .with(Text.rich("Use "))
                    .with(Text.rich("!setrsn <yourRSN> ").withBold())
                    .with(Text.rich("to store your name")));

            return;
        }

        ArrayList<String> existing = FileUtilities.readTextFile("data/rsn.txt");
        HashMap<String, String> pairs = new HashMap();

        for(String pair : existing){

            String[] split = pair.split(",");
            pairs.put(split[0], split[1]);

        }

        pairs.remove(participantToUser(msg.getSender()).getUsername());
        pairs.put(participantToUser(msg.getSender()).getUsername(), rsn);

        FileUtilities.emptyFile("data/rsn.txt");

        for(Map.Entry<String,String> entry : pairs.entrySet()){
            FileUtilities.writeToTextFile("data/", "rsn.txt", entry.getKey() + "," + entry.getValue());
        }

        msg.getChat().sendMessage(participantToUser(msg.getSender()).getUsername() + " is now known as: " + rsn);
    }
}
