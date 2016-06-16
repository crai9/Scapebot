package com.craiig.scapebot.commands;

import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Craig on 09/03/2016, 01:50.
 */
public class FeelsGoodMan extends Command {

    public String getName(){
        return "feelsgoodman";
    }

    public void run(ChatMessage msg, List<Command> commands) throws ConnectionException {

        File feels = new File("img/feelsgoodman.png");

        try {
            msg.getChat().sendImage(feels);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
