package com.craiig.scapebot.commands;

import com.craiig.scapebot.utilities.CommonUtilities;
import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ChatNotFoundException;
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
import java.util.*;

import static com.craiig.scapebot.utilities.CommonUtilities.parseXP;
import static com.craiig.scapebot.utilities.CommonUtilities.participantToUser;

public class Activity extends Command {

    public String getName(){
        return "activity";
    }

    public String[] getAliases(){
        return new String[] {"bork-log", "fury-log", "borklog", "furylog", "bork", "borkcounter", "bork-counter"};
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
                Collections.reverse(entries);

                Iterator itEntries = entries.iterator();

                String message = "";

                while (itEntries.hasNext()) {

                    SyndEntry entry = (SyndEntry) itEntries.next();
                    message += parseXP(entry.getTitle()) + System.lineSeparator();

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
