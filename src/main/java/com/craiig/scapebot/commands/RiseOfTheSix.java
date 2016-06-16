package com.craiig.scapebot.commands;

import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;
import com.samczsun.skype4j.formatting.Text;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class RiseOfTheSix extends Command {

    public String getName(){
        return "rots";
    }

    public void run(ChatMessage msg, List<Command> commands) throws ConnectionException {

        List<String[]> rots = new ArrayList<>();
        rots.add(0, new String[6]);

        DateTime now = new DateTime(DateTimeZone.UTC);

        long start = 1396699200000L;

        BigDecimal ms = new BigDecimal(now.getMillis() - start);
        BigDecimal day = new BigDecimal(24*60*60*1000);

        BigDecimal result = ms.divide(day, 10, RoundingMode.HALF_UP);

        double daysSinceStart = Math.round(result.doubleValue());

        int currentRots = (int) daysSinceStart%20;
        int tomorrowRots = (currentRots + 1 == 20) ? 0 : currentRots + 1;


        msg.getChat().sendMessage(Message.create()
            .with(Text.rich("Today's rotation: "))
            .with(Text.rich(getBrothersNames(currentRots)).withBold())
            .with(Text.NEW_LINE)
            .with(Text.rich("Tomorrow's rotation: "))
            .with(Text.rich(getBrothersNames(tomorrowRots)).withBold())
        );
    }

    private String getBrothersNames(int index){

        HashMap brothers = new HashMap();

        brothers.put('A', "Ahrim");
        brothers.put('G', "Guthan");
        brothers.put('D', "Dharok");
        brothers.put('K', "Karil");
        brothers.put('T', "Torag");
        brothers.put('V', "Verac");

        String[] rotations = new String[20];

        rotations[0] = "AGD-KTV";
        rotations[1] = "KAD-GTV";
        rotations[2] = "ATD-KGV";
        rotations[3] = "ADV-KTG";
        rotations[4] = "KAG-TDV";
        rotations[5] = "ATG-KDV";//today
        rotations[6] = "AGV-KTD";//next
        rotations[7] = "KAT-GDV";
        rotations[8] = "KAV-DTG";
        rotations[9] = "ATV-KDG";
        rotations[10] = "KDG-ATV";
        rotations[11] = "DTG-KAV";
        rotations[12] = "GDV-KAT";
        rotations[13] = "KTD-AGV";
        rotations[14] = "KDV-ATG";
        rotations[15] = "DTV-KAG";
        rotations[16] = "KTG-ADV";
        rotations[17] = "KGV-ATD";
        rotations[18] = "GTV-KAD";
        rotations[19] = "KTV-AGD";

        String response = "";
        String current = rotations[index];

        for(int i = 0; i < current.length(); i++){

            char c = current.charAt(i);
            if(c == '-'){
                response = response.substring(0, response.length()-2) + " ";
                response += "- ";
            } else{
                response += brothers.get(c) + ", ";
            }

        }

        return response.substring(0, response.length()-2);
    }
}
