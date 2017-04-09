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

public class Skill extends Command {

    public String getName(){
        return "skill";
    }

    public String[] getAliases(){
        return DataSets.getSkillID().keySet().toArray(new String[DataSets.getSkillID().size()]);
    }

    public void run(ChatMessage msg, List<Command> commands, String trigger) throws ConnectionException {

        if(trigger.equals("skill")){
            msg.getChat().sendMessage(Message.create()
                    .with(Text.rich("No RSN or Skill supplied, use "))
                    .with(Text.rich("!setrsn <yourRSN> ").withBold())
                    .with(Text.rich("to store your name, "))
                    .with(Text.rich("then use  "))
                    .with(Text.rich("!<skill> ").withBold())
                    .with(Text.rich("or just use "))
                    .with(Text.rich("!<skill> <RSN>").withBold()));
            return;
        }

        String rsn = CommonUtilities.getRSN(participantToUser(msg.getSender()).getUsername(), msg, trigger);

        if(rsn == null){
            msg.getChat().sendMessage(Message.create()
                    .with(Text.rich("No RSN supplied, use "))
                    .with(Text.rich("!setrsn <yourRSN> ").withBold())
                    .with(Text.rich("to store your name or use "))
                    .with(Text.rich("!<skill> <RSN>").withBold()));
            return;
        }

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
                    .with(Text.rich("Something broke"))
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

        HashMap<String, Integer> skills = DataSets.getSkillID();

        if(skills.containsKey(trigger.toLowerCase())){

            int skillID = skills.get(trigger.toLowerCase());

            for(Skillvalue skillvalue : profile.getSkillvalues()){
                if(skillvalue.getId() == skillID){

                    msg.getChat().sendMessage(Message.create()
                            .with(Text.rich(profile.getName() + "'s " + trigger.substring(0, 1).toUpperCase() + trigger.substring(1)).withUnderline())
                            .with(Text.NEW_LINE)
                            .with(Text.NEW_LINE)
                            .with(Text.rich("Level: "))
                            .with(Text.rich(skillvalue.getLevel().toString()).withBold())
                            .with(Text.NEW_LINE)
                            .with(Text.rich("Rank: "))
                            .with(Text.rich(parseXPPlain(skillvalue.getRank().toString())).withBold())
                            .with(Text.NEW_LINE)
                            .with(Text.rich("XP: "))
                            .with(Text.rich(parseXPPlain(skillvalue.getXp().toString().substring(0, skillvalue.getXp().toString().length()-1))).withBold())
                    );
                    return;
                }
            }
        }

    }
}
