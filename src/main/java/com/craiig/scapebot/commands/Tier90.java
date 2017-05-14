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
import static com.craiig.scapebot.utilities.CommonUtilities.logSysMessage;

public class Tier90 extends Command {

    public String getName(){
        return "t90";
    }

    public String[] getAliases(){
        return new String[] {"t92", "tier90", "tier92"};
    }

    public void run(ChatMessage msg, List<Command> commands, String trigger) throws ConnectionException {

        String searchURL = "http://services.runescape.com/m=forum/sl=0/searchthreads.ws?search=submit&srcstr=High%20Level%20Weapon%20Status";
        String forumBaseUrl = "http://services.runescape.com/m=forum/sl=0/";
        Document searchResults = Jsoup.parse(getTextFromUrl(searchURL));
        String lastPageLink = null;

        try{

            Elements results = searchResults.select(".thread-plate__title-link");
            String relLink = results.first().attr("href");
            lastPageLink = forumBaseUrl + relLink + ",goto,1000";

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

                if(countNumbersInString(e.text()) >= 41){

                    String time = "<u><b>Time posted: " + e.parent().nextElementSibling().text() + "</b></u>\n\n";
                    String contents = e.html().replace("\n", "").replace("<br>", "\n");

                    String disclaimer = "\n<i><a href='"
                            + lastPageLink
                            + "'>Incorrect? Here's the thread</a></i>";

                    msg.getChat().sendMessage(Message.fromHtml(time + contents + disclaimer));

                    return;
                }
            }


            msg.getChat().sendMessage(Message.fromHtml("Couldn't retrieve post"
                    + System.lineSeparator() + "<a href='"
                    + lastPageLink
                    + "'>Here's the thread</a>"));
            return;

        }catch (NullPointerException ex){

            if(!lastPageLink.isEmpty()){
                msg.getChat().sendMessage(Message.fromHtml("Couldn't retrieve post"
                        + System.lineSeparator() + "<a href='"
                        + lastPageLink
                        + "'>Here's the thread</a>"));
                return;
            }

            msg.getChat().sendMessage("Couldn't find thread :(");
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
