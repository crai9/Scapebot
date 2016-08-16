package com.craiig.scapebot.commands;

import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;
import com.samczsun.skype4j.formatting.Text;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


/**
 * Created by Craig on 23/03/2016, 04:05.
 */
public class Vorago extends Command {

    public String getName(){
        return "vorago";
    }

    public String[] getAliases(){
        return new String[] {"rago"};
    }

    public void run(ChatMessage msg, List<Command> commands, String trigger) throws ConnectionException {

        String[] rotations = {"Ceilings", "Scopulus", "Vitalis", "Green Bomb", "TeamSplit", "Purple Bomb"};

        DateTime now = new DateTime(DateTimeZone.UTC);

        long start = 1415797200000L;

        BigDecimal ms = new BigDecimal(now.getMillis() - start);
        BigDecimal day = new BigDecimal(24*60*60*1000);

        BigDecimal result = ms.divide(day, 10, RoundingMode.HALF_UP);

        double daysSinceStart = Math.round(result.doubleValue());
        int diffInWeeks = (int) Math.floor(daysSinceStart/7);
        int currentRotation = (diffInWeeks % 6);
        int nextRotation = (currentRotation + 1 == 6) ? 0 : currentRotation + 1;

        msg.getChat().sendMessage(Message.create()
            .with(Text.rich("Today's rotation: "))
            .with(Text.rich(rotations[currentRotation]).withBold())
            .with(Text.NEW_LINE)
            .with(Text.rich("Next rotation: "))
            .with(Text.rich(rotations[nextRotation]).withBold()));
    }
}
