package com.craiig.scapebot.commands;

import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.util.Collections;
import java.util.List;

import static com.craiig.scapebot.utilities.CommonUtilities.getTextFromUrl;

public class Tier90 extends Command {

    public String getName(){
        return "t90";
    }

    public String[] getAliases(){
        return new String[] {"t92", "tier90", "tier92"};
    }

    public void run(ChatMessage msg, List<Command> commands, String trigger) throws ConnectionException {

        long startTime = System.nanoTime();

        String searchURL = "http://services.runescape.com/m=forum/sl=0/searchthreads.ws?search=submit&srcstr=High%20Level%20Weapon%20Status";
        String forumBaseUrl = "http://services.runescape.com/m=forum/sl=0/";
        Document searchResults = Jsoup.parse(getTextFromUrl(searchURL));

        try{

            Elements results = searchResults.select(".thread-plate__title-link");
            String relLink = results.first().attr("href");
            String lastPageLink = forumBaseUrl + relLink + ",goto,1000";

            Document lastThreadPage = Jsoup.parse(getTextFromUrl(lastPageLink));
            String secondLastRelLink = lastThreadPage.select(".previous").first().attr("href");
            String secondLastPageLink = forumBaseUrl + "forums.ws" + secondLastRelLink;
            Document secondLastThreadPage = Jsoup.parse(getTextFromUrl(secondLastPageLink));

            Elements lastPagePosts = lastThreadPage.select(".forum-post__body");
            Elements secondLastPagePosts = secondLastThreadPage.select(".forum-post__body");

            Elements all = new Elements();
            all.addAll(secondLastPagePosts);
            all.addAll(lastPagePosts);
            Collections.reverse(all);
            
            for (Element e: all) {

                if(countNumbersInString(e.text()) >= 40){

                    String time = "<u><b>Time posted: " + e.parent().nextElementSibling().text() + "</b></u>\n\n";
                    String contents = e.html().replace("\n", "").replace("<br>", "\n");
                    long endTime = System.nanoTime();
                    long duration = (endTime - startTime) / 1000000;
                    String disclaimer = "\n<i>" + "[Beta command - Execution time: " + duration + "ms]</i>";

                    msg.getChat().sendMessage(Message.fromHtml(time + contents + disclaimer));

                    return;
                }
            }

        }catch (NullPointerException ex){
            System.out.println("Couldn't find thread :(");
        }
    }

    private static int countNumbersInString(String words){
        int count = 0;
        for (int i = 0, len = words.length(); i < len; i++) {
            if (Character.isDigit(words.charAt(i))) {
                count++;
            }
        }
        return count;
    }
}
