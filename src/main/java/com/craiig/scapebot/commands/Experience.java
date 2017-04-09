package com.craiig.scapebot.commands;

import com.craiig.scapebot.models.ExperienceRow;
import com.craiig.scapebot.models.Profile;
import com.craiig.scapebot.models.Skillvalue;
import com.craiig.scapebot.utilities.CommonUtilities;
import com.craiig.scapebot.utilities.DataSets;
import com.google.gson.Gson;
import com.jakewharton.fliptables.FlipTableConverters;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;
import com.samczsun.skype4j.formatting.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.craiig.scapebot.utilities.CommonUtilities.parseXPPlain;
import static com.craiig.scapebot.utilities.CommonUtilities.participantToUser;

public class Experience extends Command {

    public String getName(){
        return "xp";
    }

    public String[] getAliases(){
        return new String[] {"experience"};
    }

    public void run(ChatMessage msg, List<Command> commands, String trigger) throws ConnectionException {

        String rsn = CommonUtilities.getRSN(participantToUser(msg.getSender()).getUsername(), msg, trigger);

        if(rsn == null){
            //No RSN stored
            msg.getChat().sendMessage(Message.create()
                    .with(Text.rich("No RSN supplied, use "))
                    .with(Text.rich("!setrsn <yourRSN> ").withBold())
                    .with(Text.rich("to store your name or use "))
                    .with(Text.rich("!xp <RSN>").withBold()));
            return;
        }

        HashMap<Integer, String> skills = DataSets.skillShortNames();
        List<ExperienceRow> data = new ArrayList();
        Profile profile = null;

        try {

            HttpResponse<String> response = Unirest.get("https://apps.runescape.com/runemetrics/profile/profile")
                    .queryString("activities", 20)
                    .queryString("user", rsn)
                    .header("Accept", "text/plain")
                    .asString();

            Gson gson = new Gson();
            profile = gson.fromJson(response.getBody(), Profile.class);

        } catch (Exception ex){
            System.out.println(ex.getMessage());
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

        data.add(new ExperienceRow(parseXPPlain(profile.getTotalxp().toString()), "Total"));
        for(Skillvalue skillvalue : profile.getSkillvalues()){
            data.add(new ExperienceRow(parseXPPlain(skillvalue.getXp().toString().substring(0, skillvalue.getXp().toString().length()-1)), skills.get(skillvalue.getId())));
        }

        msg.getChat().sendMessage(Message.create()
                .with(Text.rich(FlipTableConverters.fromIterable(data, ExperienceRow.class)).withCode())
        );



    }
}
