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


        String pageURL = "http://warbandtracker.com/goldberg/index.php";
        Document searchResults = Jsoup.parse(getTextFromUrl(pageURL));

        try{

            Element table = searchResults.select(".contentBlock#cape").first();
            Elements imgs = table.getElementsByTag("img");

            String message = "First rune: <b>"
                    + imgs.get(0).attr("alt")
                    + "</b>"
                    + "Second rune: <b>"
                    + imgs.get(1).attr("alt")
                    + imgs.get(2).attr("alt")
                    + imgs.get(3).attr("alt")
                    + "</b>";

            message = message.replace("\r\n", " ");
            message = message.replace("Reported by ", "(");
            message = message.replace("Second ", "Second ");
            message = message.replace("%.", "%)\n");
            message += "\nData taken from <a href='" + pageURL +"'>warbandtracker.com</a>.";

            msg.getChat().sendMessage(Message.fromHtml(message));

        }catch (NullPointerException ex){

            msg.getChat().sendMessage(Message.fromHtml(
                    "Couldn't find combo :( \n try <a href='"
                    + pageURL
                    + "'>warbandtracker.com</a>.")
            );
        }
    }

}
