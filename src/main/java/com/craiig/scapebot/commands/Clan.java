package com.craiig.scapebot.commands;

import com.craiig.scapebot.models.PlayerDetails;
import com.craiig.scapebot.utilities.CommonUtilities;
import com.google.gson.Gson;
import com.samczsun.skype4j.chat.Chat;
import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;
import com.samczsun.skype4j.formatting.Text;

import java.util.ArrayList;
import java.util.List;

import static com.craiig.scapebot.utilities.CommonUtilities.participantToUser;

public class Clan extends Command {

    public String getName(){
        return "clan";
    }

    public String[] getAliases(){
        return new String[] {};
    }

    public void run(ChatMessage msg, List<Command> commands, String trigger) throws ConnectionException {

        String rsn = CommonUtilities.getRSN(participantToUser(msg.getSender()).getUsername(), msg, trigger);

        if(rsn == null){
            //No RSN stored
            msg.getChat().sendMessage(Message.create()
                    .with(Text.rich("No RSN supplied, use "))
                    .with(Text.rich("!setrsn <yourRSN> ").withBold())
                    .with(Text.rich("to store your name or use "))
                    .with(Text.rich("!clan <RSN>").withBold()));
            return;
        }

        String apiUrl = "http://services.runescape.com/m=website-data/playerDetails.ws?names=%5B%22" + rsn + "%22%5D&callback=jQuery000000000000000_0000000000&_=0";

        String response = CommonUtilities.getTextFromUrl(apiUrl);
        String text = response.split("\\]")[0].split("\\[")[1];

        Gson gson = new Gson();
        PlayerDetails details = gson.fromJson(text, PlayerDetails.class);

        String message;

        if(details.getClan() == null){

            message = details.getName() + " is not in a clan.";

        } else {

            String end = (details.getRecruiting() == "true") ? "are recruiting!" : "are not recruiting.";

            message = "Clan is <b>" + details.getClan() + "</b> and they " + end;
        }

        msg.getChat().sendMessage(Message.fromHtml(message));

    }
}
