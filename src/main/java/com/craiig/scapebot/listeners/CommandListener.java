package com.craiig.scapebot.listeners;

import com.craiig.scapebot.commands.Activity;
import com.craiig.scapebot.commands.Command;
import com.craiig.scapebot.commands.Hey;
import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.events.EventHandler;
import com.samczsun.skype4j.events.Listener;
import com.samczsun.skype4j.events.chat.message.MessageEvent;
import com.samczsun.skype4j.exceptions.ConnectionException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Craig on 01/03/2016, 21:05, 01:16.

 */

public class CommandListener implements Listener {

    private final Skype skype;
    private List<Command> commands = new ArrayList<>();

    public CommandListener(Skype skype){
        this.skype = skype;
        commands.add(new Hey());
        commands.add(new Activity());
    }

    @EventHandler
    public void onMessage(MessageEvent e) {

        try {

            if(e.getMessage().getContent().asPlaintext().startsWith("!") && e.getMessage().getContent().asPlaintext().length() > 1){

                String command = e.getMessage().getContent().asPlaintext().trim().substring(1).split(" ")[0].toLowerCase();

                for(Command cmd : commands){
                    if(command.equals(cmd.getName())){

                        cmd.run(e.getMessage());

                    }
                }
            }

        } catch (ConnectionException ex) {
            System.out.println(ex.getMessage());
        }

    }

}
