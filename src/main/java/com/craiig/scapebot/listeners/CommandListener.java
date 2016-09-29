package com.craiig.scapebot.listeners;

import com.craiig.scapebot.commands.*;
import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.events.EventHandler;
import com.samczsun.skype4j.events.Listener;
import com.samczsun.skype4j.events.chat.message.MessageEvent;
import com.samczsun.skype4j.exceptions.ConnectionException;

import java.util.ArrayList;
import java.util.List;

import static com.craiig.scapebot.utilities.CommonUtilities.log;
/**
 * Created by Craig on 01/03/2016, 21:05, 01:16.

 */

public class CommandListener implements Listener {

    private final Skype skype;
    private List<Command> commands = new ArrayList<>();

    public CommandListener(Skype skype){

        this.skype = skype;

        //TODO: Probably order these by most used, should speed things up

        commands.add(new Commands());
        commands.add(new Activity());
        commands.add(new VoiceOfSeren());
        commands.add(new Warbands());
        commands.add(new Shutdown());
        commands.add(new SetRSN());
        commands.add(new Vorago());
        commands.add(new Araxxor());
        commands.add(new RiseOfTheSix());
        commands.add(new ActivityPlus());
        commands.add(new Clan());
        commands.add(new Title());
        commands.add(new Emotes());
        commands.add(new Wiki());
        commands.add(new ToggleNews());
        commands.add(new Tier90());

    }

    @EventHandler
    public void onMessage(MessageEvent e) {

        try {

            if(e.getMessage().getContent().asPlaintext().startsWith("!") && e.getMessage().getContent().asPlaintext().length() > 1){

                String command = e.getMessage().getContent().asPlaintext().trim().substring(1).split(" ")[0].toLowerCase();

                for(Command cmd : commands){
                    if(command.equals(cmd.getName())){

                        e.getChat().startTyping();
                        cmd.run(e.getMessage(), commands, cmd.getName());
                        e.getChat().stopTyping();

                        skype.loadAllContacts();
                        return;

                    } else {

                        for(String alias : cmd.getAliases()){
                            if(command.equals(alias.toLowerCase())) {

                                e.getChat().startTyping();
                                cmd.run(e.getMessage(), commands, alias.toLowerCase());
                                e.getChat().stopTyping();

                                skype.loadAllContacts();
                                return;

                            }
                        }
                    }
                }
            }

            skype.loadAllContacts();
            return;

        } catch (ConnectionException ex) {
            log(ex.getMessage());
        }


    }

}
