package com.craiig.scapebot.commands;

import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Craig on 09/03/2016, 01:50.
 */
public class Kappa extends Command {

    public String getName(){
        return "kappa";
    }

    public void run(ChatMessage msg, List<Command> commands) throws ConnectionException {

        File kappa = new File("img/kappa.png");

        try {
            msg.getChat().sendImage(kappa);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
