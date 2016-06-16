package com.craiig.scapebot.commands;

import com.craiig.scapebot.models.PlayerDetails;
import com.craiig.scapebot.utilities.CommonUtilities;
import com.google.gson.Gson;
import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;

import java.util.List;

public class Title extends Command {

    public String getName(){
        return "title";
    }

    public void run(ChatMessage msg, List<Command> commands) throws ConnectionException {

        String rsn = CommonUtilities.getRSN(msg.getSender().getUsername(), msg, getName());

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

        String title = "<b>" + details.getTitle() + "</b>";
        String message;

        if(details.getIsSuffix().equals("true")){
            message = details.getName() + " " + title;
        } else {
            message = title + " " + details.getName();
        }

        msg.getChat().sendMessage(Message.fromHtml(message));

    }
}
