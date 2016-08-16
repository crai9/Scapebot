package com.craiig.scapebot.commands;

import com.craiig.scapebot.utilities.CommonUtilities;
import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;

import java.util.Date;
import java.util.List;

/**
 * Created by Craig on 12/03/2016, 21:29.
 */
public class Warbands extends Command{

    public String getName(){
        return "wbs";
    }

    public String[] getAliases(){
        return new String[] {"warbands", "bands"};
    }

    public void run(ChatMessage msg, List<Command> commands, String trigger) throws ConnectionException {

        long millis = (25200 - ((new Date().getTime() / 1000 - 1376222417) % 25200)) * 1000;
        msg.getChat().sendMessage(CommonUtilities.msToHMS(millis) + " until the next Warband.");
    }
}
