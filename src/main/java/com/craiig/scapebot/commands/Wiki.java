package com.craiig.scapebot.commands;

import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;

import java.util.List;

public class Wiki extends Command {

    public String getName() {
        return "wiki";
    }

    public String[] getAliases(){
        return new String[] {"rw", "wikia"};
    }

    public void run(ChatMessage msg, List<Command> commands, String trigger) throws ConnectionException {


        String query = msg.getContent().asPlaintext().replace("!" + trigger, "").trim();

        if(query.length() < 1){
            msg.getChat().sendMessage("To search RuneScape Wiki, use: !wiki <query>");
            return;
        }

        query = query.replace(" ", "_");

        msg.getChat().sendMessage("http://runescape.wikia.com/wiki/Special:Search?search=" + query);

    }
}
