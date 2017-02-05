package com.craiig.scapebot.commands;

import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;
import com.samczsun.skype4j.formatting.Text;

import java.util.List;

import static com.craiig.scapebot.utilities.CommonUtilities.getTextFromUrl;

public class Price extends Command {

    public String getName(){
        return "price";
    }

    public String[] getAliases(){
        return new String[] {"pc", "pricecheck"};
    }

    public void run(ChatMessage msg, List<Command> commands, String trigger) throws ConnectionException {

        String query = msg.getContent().asPlaintext().replace("!" + trigger, "").trim().replace(" ", "+");

        if(query.length() < 1){
            msg.getChat().sendMessage(Message.create()
                    .with(Text.rich("No item queried, use "))
                    .with(Text.rich("!price <query>").withBold())
            );
            return;
        }

        try {

            String queryURL = "http://rscript.org/lookup.php?type=ge&search=" + query;
            String response = getTextFromUrl(queryURL);

            response = response.split("RESULTS: ")[1];
            int results = Integer.parseInt(response.split("\n")[0]); //change to take full number

            if (results == 0) {
                msg.getChat().sendMessage(Message.create()
                        .with(Text.rich("No results"))
                );

                return;
            }

            if (results > 1) {
                msg.getChat().sendMessage(Message.create()
                        .with(Text.rich(results + " results, refine search"))
                );

                return;
            }

            if (results == 1) {

                String itemString = response.split("\nITEM: ")[1].split("\n")[0];
                String[] itemDetails = itemString.split(" ");

                msg.getChat().sendMessage(Message.create()
                        .with(Text.rich("Item: "))
                        .with(Text.rich(itemDetails[1].replace("_", " ")).withBold())
                        .with(Text.NEW_LINE)
                        .with(Text.rich("Current price: "))
                        .with(Text.rich(itemDetails[2]).withBold())
                        .with(Text.NEW_LINE)
                        .with(Text.rich("Change today: "))
                        .with(Text.rich(itemDetails[3]).withBold())
                        .with(Text.NEW_LINE)
                );

                return;
            }


        }catch (IndexOutOfBoundsException ex){
            msg.getChat().sendMessage(Message.create()
                    .with(Text.rich("Error"))
            );
        }

    }
}