package com.craiig.scapebot.commands;

import com.craiig.scapebot.utilities.CommonUtilities;
import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static com.craiig.scapebot.utilities.CommonUtilities.parseXP;

public class ActivityPlus extends Command {

    public String getName(){
        return "activity+";
    }

    public void run(ChatMessage msg, List<Command> commands) throws ConnectionException {

        String rsn = CommonUtilities.getRSN(msg.getSender().getUsername(), msg, getName());

        if(rsn == null){
            //No RSN stored
            msg.getChat().sendMessage("No RSN supplied");
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
                Collections.reverse(entries);

                Iterator itEntries = entries.iterator();

                String message = "";

                while (itEntries.hasNext()) {

                    SyndEntry entry = (SyndEntry) itEntries.next();
                    message += "<b>" + parseXP(entry.getTitle()) + "</b>";
                    message += parseXP(entry.getDescription().getValue()) + System.lineSeparator();

                }

                msg.getChat().sendMessage(Message.fromHtml(message));

            }catch (FileNotFoundException ex){
                msg.getChat().sendMessage("Activities for that user could not be found.");
            }

        }catch (FeedException | IOException  ex){
            ex.printStackTrace();
        }

    }

}
