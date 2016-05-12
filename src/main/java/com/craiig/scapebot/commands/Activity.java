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
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.MalformedInputException;
import java.text.NumberFormat;
import java.util.*;

/**
 * Created by Craig on 07/03/2016, 20:20.
 */
public class Activity extends Command {

    public String getName(){
        return "activity";
    }

    public void run(ChatMessage msg) throws ConnectionException {

        //Parameter supplied as rsn
        String rsn = msg.getContent().asPlaintext().replace("!activity ", "");

        if(msg.getContent().asPlaintext().length() < 11){

            //No parameter supplied as RSN
            //Check if we already know RSN
            rsn = CommonUtilities.getRSN(msg.getSender().getUsername());
            if(rsn == null){
                //No RSN stored
                msg.getChat().sendMessage("No RSN supplied");
                return;
            }

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
                msg.getChat().sendMessage("Activities for that user could not be found.");
            }

        }catch (FeedException | IOException  ex){
            ex.printStackTrace();
        }

    }

    public static String parseXP(String line){

        String[] sections = line.split("XP");
        try {

            String formatted = NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(sections[0]));
            return formatted + "XP" + sections[1];

        } catch (NumberFormatException e) {
            return line;
        }
    }
}
