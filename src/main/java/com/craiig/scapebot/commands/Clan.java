package com.craiig.scapebot.commands;

import com.craiig.scapebot.models.PlayerDetails;
import com.craiig.scapebot.utilities.CommonUtilities;
import com.google.gson.Gson;
import com.samczsun.skype4j.chat.Chat;
import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;

import java.util.ArrayList;
import java.util.List;

public class Clan extends Command {

    public String getName(){
        return "clan";
    }

    public String[] getAliases(){
        return new String[] {};
    }

    public void run(ChatMessage msg, List<Command> commands, String trigger) throws ConnectionException {

        String rsn = CommonUtilities.getRSN(msg.getSender().getUsername(), msg, trigger);

        if(rsn == null){
            //No RSN stored
            msg.getChat().sendMessage("No RSN supplied");
            return;
        }

        String apiUrl = "http://services.runescape.com/m=website-data/playerDetails.ws?names=%5B%22" + rsn + "%22%5D&callback=jQuery000000000000000_0000000000&_=0";

        String response = CommonUtilities.getTextFromUrl(apiUrl);
        String text = response.split("\\]")[0].split("\\[")[1];

        Gson gson = new Gson();
        PlayerDetails details = gson.fromJson(text, PlayerDetails.class);

        String end = (details.getRecruiting() == "true") ? "are recruiting!" : "are not recruiting.";
        String message = "Clan is <b>" + details.getClan() + "</b> and they " + end;

        msg.getChat().sendMessage(Message.fromHtml(message));

    }
}
