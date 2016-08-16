package com.craiig.scapebot.commands;

import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;
import com.samczsun.skype4j.formatting.Text;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


/**
 * Created by Craig on 25/03/2016, 16:44.
 */

public class Araxxor extends Command {

    public String getName(){
        return "araxxor";
    }

    public String[] getAliases(){
        return new String[] {"raxx", "rax", "spider", "arax"};
    }

    public void run(ChatMessage msg, List<Command> commands, String trigger) throws ConnectionException {

        String[] rotations = {"1 & 2", "2 & 3", "1 & 3"};

        DateTime now = new DateTime(DateTimeZone.UTC);

        long start = 1413633600000L;

        BigDecimal ms = new BigDecimal(now.getMillis() - start);
        BigDecimal day = new BigDecimal(24*60*60*1000);

        BigDecimal result = ms.divide(day, 10, RoundingMode.HALF_UP);

        double daysSinceStart = Math.round(result.doubleValue());
        int diffInRotations = (int) Math.floor(daysSinceStart/4);

        int nextAraxRotation = (int) (4 - daysSinceStart%4);
        int currentAraxRotation = diffInRotations%3;
        int tomorrowAraxRotation = (currentAraxRotation + 1 == 3) ? 0 : currentAraxRotation + 1;

        String resets = (nextAraxRotation > 1) ? " resets" : " reset";

        msg.getChat().sendMessage(Message.create()
            .with(Text.rich("Today's rotation: "))
            .with(Text.rich(rotations[currentAraxRotation]).withBold())
            .with(Text.NEW_LINE)
            .with(Text.rich("Next rotation: "))
            .with(Text.rich(rotations[tomorrowAraxRotation]).withBold())
            .with(Text.rich(" begins after "))
            .with(Text.rich(nextAraxRotation + resets).withBold())
        );
    }
}
