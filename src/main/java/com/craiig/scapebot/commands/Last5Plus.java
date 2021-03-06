package com.craiig.scapebot.commands;

import com.craiig.scapebot.utilities.CommonUtilities;
import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;
import com.samczsun.skype4j.formatting.Text;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static com.craiig.scapebot.utilities.CommonUtilities.parseXP;
import static com.craiig.scapebot.utilities.CommonUtilities.participantToUser;

public class Last5Plus extends Command {

    public String getName(){
        return "last+";
    }

    public String[] getAliases(){
        return new String[] {"5+", "last5+"};
    }

    public void run(ChatMessage msg, List<Command> commands, String trigger) throws ConnectionException {

        String rsn = CommonUtilities.getRSN(participantToUser(msg.getSender()).getUsername(), msg, trigger);

        if(rsn == null){
            //No RSN stored
            msg.getChat().sendMessage(Message.create()
                    .with(Text.rich("No RSN supplied, use "))
                    .with(Text.rich("!setrsn <yourRSN> ").withBold())
                    .with(Text.rich("to store your name or use "))
                    .with(Text.rich("!activity <RSN>").withBold()));
            return;
        }

        try{

            String baseURL = "http://services.runescape.com/m=adventurers-log/rssfeed?searchName=";

            URL url = new URL(baseURL + rsn);

            try {

                HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();

                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(new XmlReader(httpcon));
                List entries = feed.getEntries();

                Iterator itEntries = entries.iterator();

                String message = "";

                int count = 0;
                ArrayList<String> items = new ArrayList<>();

                while (itEntries.hasNext() && count < 5) {

                    SyndEntry entry = (SyndEntry) itEntries.next();
                    items.add(parseXP(entry.getDescription().getValue().trim()) + System.lineSeparator());
                    items.add("<b>" + parseXP(entry.getTitle().trim()) + "</b>" + System.lineSeparator());
                    count++;

                }

                Collections.reverse(items);
                for (String item: items) {
                    message += item;
                }
                msg.getChat().sendMessage(Message.fromHtml(message));

            }catch (FileNotFoundException ex){
                msg.getChat().sendMessage("Activities for '" + rsn + "' could not be found.");
            }

        }catch (FeedException | IOException  ex){
            ex.printStackTrace();
        }

    }

}
