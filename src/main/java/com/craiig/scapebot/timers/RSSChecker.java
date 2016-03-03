package com.craiig.scapebot.timers;

import com.craiig.scapebot.utilities.FileUtilities;
import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.chat.Chat;
import com.samczsun.skype4j.exceptions.ChatNotFoundException;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;
import com.samczsun.skype4j.formatting.Text;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * Created by craig on 02/03/2016.
 */
public class RSSChecker {

    private Skype skype;

    public RSSChecker(Skype skype){
        this.skype = skype;
    }

    public void start(){

        Timer timer = new Timer("RSS Timer");
        TimerTask timertask;

        timertask = new TimerTask() {

          @Override
            public void run(){
              System.out.println("Checking RSS Feed");

              try {
                  URL url = new URL("http://services.runescape.com/m=news/latest_news.rss");

                  HttpURLConnection httpcon = (HttpURLConnection)url.openConnection();

                  SyndFeedInput input = new SyndFeedInput();
                  SyndFeed feed = input.build(new XmlReader(httpcon));
                  List entries = feed.getEntries();

                  Iterator itEntries = entries.iterator();

                  FileUtilities.directoryExists("data");

                  ArrayList<String> existing;
                  ArrayList<String> fresh = new ArrayList<>();


                  while (itEntries.hasNext()) {

                      SyndEntry entry = (SyndEntry) itEntries.next();

                      String data = entryToString(entry);

                      if(FileUtilities.readTextFile("data/news.txt").size() <= 14){
                          //Not been written to before

                          FileUtilities.writeToTextFile("data", "/news.txt", data);
                          System.out.println(data);
                      }

                      fresh.add(data);

                  }

                  existing = FileUtilities.readTextFile("data/news.txt");

                  fresh.removeAll(existing);

                  System.out.println("Number of new articles: " + fresh.size());

                  if(fresh.size() > 0){
                      for(String s : fresh){
                          FileUtilities.writeToTextFile("data", "/news.txt", s);
                          String[] news = s.split("@@@");

                          //TODO: Send to all chats that are accepting
                          Chat chat = skype.getOrLoadChat("8:theoptimisticcow");

                          chat.sendMessage(Message.create()
                                  .with(Text.rich(news[0]).withBold())
                                  .with(Text.NEW_LINE)
                                  .with(Text.rich(news[3]).withItalic()));
                          chat.sendMessage(news[1]);
                      }
                  }

              } catch (FeedException | IOException | ConnectionException | ChatNotFoundException e) {
                  e.printStackTrace();
              }


          }
        };

        timer.scheduleAtFixedRate(timertask, 1000, 60000);
    }

    public static String entryToString(SyndEntry entry){
     return entry.getTitle() + "@@@" + entry.getLink() + "@@@" + entry.getPublishedDate() + "@@@" + entry.getDescription().getValue().trim();
    }
}