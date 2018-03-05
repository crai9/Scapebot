package com.craiig.scapebot.commands;

import com.craiig.scapebot.utilities.CommonUtilities;
import com.craiig.scapebot.utilities.FileUtilities;
import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;
import com.samczsun.skype4j.formatting.Text;

import java.util.*;

public class Debug extends Command {

    @Override
    public String getName() {
        return "";
    }

    public String[] getAliases(){
        return new String[] { "uptime", "debug"};
    }

    @Override
    public void run(ChatMessage msg, List<Command> commands, String trigger) throws ConnectionException {

        String timeString = "test";
        ArrayList<String> lines = FileUtilities.readTextFile("data/uptime.txt");

        Long timestamp = Long.parseLong(lines.get(0));
        Long now = new Date().getTime();

        Long diff = now - timestamp;
        timeString = CommonUtilities.msToHMS(diff);

        String message = "";
        message += "Used Memory   : " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576 + "MB \n";
        message += "Free Memory   : " + Runtime.getRuntime().freeMemory() / 1048576 + "MB \n";
        message += "Total Memory  : " + Runtime.getRuntime().totalMemory() / 1048576 + "MB \n";
        message += "Max Memory    : " + Runtime.getRuntime().maxMemory() / 1048576 + "MB \n";

        msg.getChat().sendMessage(Message.create().with(Text.rich(message).withCode())
                .with(Text.NEW_LINE)
                .with(Text.rich("Bot has been online for " + timeString).withCode())
        );

    }
}
