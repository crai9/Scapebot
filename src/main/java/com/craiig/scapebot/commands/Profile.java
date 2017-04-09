package com.craiig.scapebot.commands;

import com.craiig.scapebot.utilities.CommonUtilities;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;
import com.samczsun.skype4j.formatting.Text;


import java.util.List;

import static com.craiig.scapebot.utilities.CommonUtilities.logSysMessage;
import static com.craiig.scapebot.utilities.CommonUtilities.parseXPPlain;
import static com.craiig.scapebot.utilities.CommonUtilities.participantToUser;

public class Profile extends Command {

    public String getName(){
        return "profile";
    }

    public String[] getAliases(){
        return new String[] {"summary"};
    }

    public void run(ChatMessage msg, List<Command> commands, String trigger) throws ConnectionException {

        String rsn = CommonUtilities.getRSN(participantToUser(msg.getSender()).getUsername(), msg, trigger);

        if(rsn == null){
            //No RSN stored
            msg.getChat().sendMessage(Message.create()
                    .with(Text.rich("No RSN supplied, use "))
                    .with(Text.rich("!setrsn <yourRSN> ").withBold())
                    .with(Text.rich("to store your name or use "))
                    .with(Text.rich("!profile <RSN>").withBold()));
            return;
        }

        com.craiig.scapebot.models.Profile profile = null;

        try {

            HttpResponse<String> response = Unirest.get("https://apps.runescape.com/runemetrics/profile/profile")
                    .queryString("activities", 20)
                    .queryString("user", rsn)
                    .header("Accept", "text/plain")
                    .asString();

            Gson gson = new Gson();
            profile = gson.fromJson(response.getBody(), com.craiig.scapebot.models.Profile.class);

        } catch (Exception ex){
            logSysMessage(ex.getMessage(), "System");
            msg.getChat().sendMessage(Message.create()
                    .with(Text.rich("Something bad happened"))
            );
            return;
        }

        if(profile.getError() != null){
            if(profile.getError().equals("PROFILE_PRIVATE")){
                msg.getChat().sendMessage(Message.create()
                        .with(Text.rich("That profile is private"))
                );
                return;
            }
        }

        if(profile.getTotalxp() == null) {
            msg.getChat().sendMessage(Message.create()
                    .with(Text.rich("Stats not found"))
            );
            return;
        }

        msg.getChat().sendMessage(Message.create()

                .with(Text.rich(profile.getName()).withUnderline())
                .with(Text.NEW_LINE)
                .with(Text.NEW_LINE)
                .with(Text.rich("Overall rank: "))
                .with(Text.rich(parseXPPlain(profile.getRank().toString())).withBold())
                .with(Text.NEW_LINE)
                .with(Text.rich("Total XP: "))
                .with(Text.rich(parseXPPlain(profile.getTotalxp().toString())).withBold())
                .with(Text.NEW_LINE)
                .with(Text.rich("Total level: "))
                .with(Text.rich(parseXPPlain(profile.getTotalskill().toString())).withBold())
                .with(Text.NEW_LINE)
                .with(Text.rich("Combat level: "))
                .with(Text.rich(parseXPPlain(profile.getCombatlevel().toString())).withBold())
                .with(Text.NEW_LINE)
                .with(Text.rich("Quests done/begun/unbegun: "))
                .with(Text.rich(profile.getQuestscomplete() + "/" + profile.getQuestsstarted() + "/" + profile.getQuestsnotstarted()).withBold())

        );

    }
}
