package com.craiig.scapebot.commands;

import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Craig on 14/03/2016, 01:14.
 */
public class PJSalt extends Command {

    public String getName() {
        return "pjsalt";
    }

    public void run(ChatMessage msg, List<Command> commands) throws ConnectionException {
        File pj = new File("img/pjsalt.png");

        try {
            msg.getChat().sendImage(pj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
