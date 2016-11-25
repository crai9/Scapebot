package com.craiig.scapebot.commands;

import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;
import com.samczsun.skype4j.formatting.Text;

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
            msg.getChat().sendMessage(Message.create()
                    .with(Text.rich("To search RuneScape Wiki, use: "))
                    .with(Text.rich("!wiki <query> ").withBold()));
            return;
        }

        query = query.replace(" ", "_");

        msg.getChat().sendMessage("http://runescape.wikia.com/wiki/Special:Search?search=" + query);

    }
}
