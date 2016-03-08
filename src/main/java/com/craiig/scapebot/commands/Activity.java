package com.craiig.scapebot.commands;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Craig on 07/03/2016, 20:20.
 */
public class Activity extends Command {

    public String getName(){
        return "activity";
    }

    public void run(ChatMessage msg) throws ConnectionException {


        if(msg.getContent().asPlaintext().length() < 11){

            return;
        }

        try{

            String rsn = msg.getContent().asPlaintext().replace("!activity ", "");

            String baseURL = "http://services.runescape.com/m=adventurers-log/rssfeed?searchName=";

            URL url = new URL(baseURL + rsn);

            try {

                HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();

                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(new XmlReader(httpcon));
                List entries = feed.getEntries();
                Collections.reverse(entries);

                Iterator itEntries = entries.iterator();

                String message = new String();

                while (itEntries.hasNext()) {

                    SyndEntry entry = (SyndEntry) itEntries.next();
                    message += entry.getTitle() + System.lineSeparator();

                }

                msg.getChat().sendMessage(message);

            }catch (FileNotFoundException ex){
                msg.getChat().sendMessage("Activities for that user could not be found.");
                return;
            }

        }catch (FeedException | IOException  ex){
            ex.printStackTrace();
        }

    }
}
