package com.craiig.scapebot.commands;

import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;
import com.samczsun.skype4j.formatting.Text;

import java.util.List;
import java.util.Random;

public class Roll extends Command {

    public String getName() {
        return "roll";
    }

    public String[] getAliases(){
        return new String[] {"random", "dice"};
    }

    public void run(ChatMessage msg, List<Command> commands, String trigger) throws ConnectionException {

        String message = msg.getContent().asPlaintext().trim().replace("!" + trigger, "").trim();

        if(message.length() < 1){
            msg.getChat().sendMessage(Message.create()
                    .with(Text.rich("No limit set, use "))
                    .with(Text.rich("!roll <max number>").withBold())
                    .with(Text.NEW_LINE)
                    .with(Text.rich("or "))
                    .with(Text.rich("!roll <min number> <max number>").withBold())
                    .with(Text.NEW_LINE)
                    .with(Text.rich("to roll a random number."))
            );
            return;
        }

        int lowerLimit = 0;
        int upperLimit = 0;

        String[] split = message.split(" ");

        try {

            if (split.length == 1) {
                upperLimit = Integer.parseInt(split[0]);
            } else if (split.length >= 2) {
                lowerLimit = Integer.parseInt(split[0]);
                upperLimit = Integer.parseInt(split[1]);
            }

        }catch (NumberFormatException ex){
            msg.getChat().sendMessage(Message.create()
                    .with(Text.rich("Invalid number limit"))
            );
            return;
        }

        int random = randInt(lowerLimit, upperLimit);

        msg.getChat().sendMessage(Message.fromHtml("Rolled a <b>" + random + "</b>"));
        return;

    }

    private int randInt(int min, int max) {

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
