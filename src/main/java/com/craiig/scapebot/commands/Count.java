package com.craiig.scapebot.commands;

import com.craiig.scapebot.utilities.CommonUtilities;
import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;
import com.samczsun.skype4j.formatting.Text;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import java.util.List;

public class Count extends Command {

    public String getName() {
        return "count";
    }

    public String[] getAliases(){
        return new String[] {};
    }

    public void run(ChatMessage msg, List<Command> commands, String trigger) throws ConnectionException {

        int rSCount = -1;

        String apiUrl = "http://www.runescape.com/player_count.js?varname=iPlayerCount&callback=jQuery000000000000000_0000000000&_=0";
        String oldschoolUrl = "http://oldschool.runescape.com/";

        String response = CommonUtilities.getTextFromUrl(apiUrl);
        int totalcount = Integer.parseInt(response.split("\\)")[0].split("\\(")[1]);

        String response3 = CommonUtilities.getTextFromUrl(oldschoolUrl);
        Document doc = Jsoup.parse(response3);
        String osText = doc.select(".player-count").text();

        int oSCount = Integer.parseInt(osText.substring(0, osText.length() - 16).substring(20));
        rSCount = totalcount - oSCount;

        String rSFormatted = NumberFormat.getInstance(Locale.US).format(rSCount);
        String oSFormatted = NumberFormat.getInstance(Locale.US).format(oSCount);

        double rSPercentage = ((float)rSCount / totalcount) * 100;
        double oSPercentage = ((float)oSCount / totalcount) * 100;

        DecimalFormat df = new DecimalFormat("#.##");

        msg.getChat().sendMessage(Message.create()
                .with(Text.rich("Runescape: "))
                .with(Text.rich(rSFormatted + " (" + df.format(rSPercentage) + "%)").withBold())
                .with(Text.NEW_LINE)
                .with(Text.rich("Oldschool: "))
                .with(Text.rich(oSFormatted + " (" + df.format(oSPercentage) + "%)").withBold())

        );

    }
}
