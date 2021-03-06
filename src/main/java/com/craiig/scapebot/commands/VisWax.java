package com.craiig.scapebot.commands;

import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

import static com.craiig.scapebot.utilities.CommonUtilities.getTextFromUrl;

public class VisWax extends Command {

    public String getName(){
        return "viswax";
    }

    public String[] getAliases(){
        return new String[] {"vis", "wax"};
    }

    public void run(ChatMessage msg, List<Command> commands, String trigger) throws ConnectionException {

        String pageURL = "http://services.runescape.com/m=forum/forums.ws?75,76,331,66006366";
        Document searchResults = Jsoup.parse(getTextFromUrl(pageURL));
        String message = "";

        try{

            Elements posts = searchResults.select(".forum-post__body");
            String prepared = posts.get(0).html()
                    .split("Combination for")[1]
                    .replace("\n", "")
                    .replace("<br>", "\n")
                    .replace("<b>", "")
                    .replace("\n . \n", "\n")
                    .replace("\n \n", "\n")
                    .replace("\n\n", "\n")
                    .replace("Slot", "\nSlot")
                    .replace("  R ", "R")
                    .replace("  A ", "A")
                    .replace("  N ", "N")
                    .replace("  D ", "D")
                    .replace("  O ", "O")
                    .replace("  M ", "M")
                    .split("Alts are listed")[0];

            String[] parts = prepared.split("Slot 1:");
            message = "<b>" + parts[0] + "</b> Slot 1:" + parts[1];

            msg.getChat().sendMessage(Message.fromHtml(message));

        }catch (NullPointerException ex){

            msg.getChat().sendMessage(Message.fromHtml(
                    "Couldn't find combo :(\ntry <a href='"
                    + pageURL
                    + "'>this thread</a>.")
            );
        }
    }

}
