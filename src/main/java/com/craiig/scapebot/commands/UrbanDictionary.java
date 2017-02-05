package com.craiig.scapebot.commands;

import com.craiig.scapebot.models.UrbanDictionaryResult;
import com.google.gson.Gson;
import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;
import com.samczsun.skype4j.formatting.Text;

import java.util.List;

import static com.craiig.scapebot.utilities.CommonUtilities.*;

public class UrbanDictionary extends Command {

    public String getName(){
        return "";
    }

    public String[] getAliases(){
        return new String[] {"urbandictionary", "ud"};
    }

    public void run(ChatMessage msg, List<Command> commands, String trigger) throws ConnectionException {

        String query = msg.getContent().asPlaintext().replace("!" + trigger, "").trim().replace(" ", "+");

        if(query.length() < 1){
            msg.getChat().sendMessage(Message.create()
                    .with(Text.rich("No term queried, use "))
                    .with(Text.rich("!ud <term>").withBold())
            );
            return;
        }

        String response = getUrbanDictionaryResponse(query);

        Gson gson = new Gson();
        UrbanDictionaryResult ud = gson.fromJson(response, UrbanDictionaryResult.class);

        if(ud.getList().size() > 0){

            msg.getChat().sendMessage(Message.create()
                    .with(Text.rich(ud.getList().get(0).getWord()).withBold().withUnderline())
                    .with(Text.NEW_LINE)
                    .with(Text.NEW_LINE)
                    .with(Text.rich(ud.getList().get(0).getDefinition()))
            );

        } else {

            msg.getChat().sendMessage(Message.create()
                    .with(Text.rich("No results"))
            );

        }
    }
}
