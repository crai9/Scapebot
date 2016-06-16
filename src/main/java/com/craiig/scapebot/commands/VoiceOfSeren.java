package com.craiig.scapebot.commands;

import com.craiig.scapebot.models.VOS;
import com.craiig.scapebot.utilities.CommonUtilities;
import com.google.gson.Gson;
import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;
import com.samczsun.skype4j.formatting.Text;

import java.util.List;

/**
 * Created by Craig on 12/03/2016, 21:17.
 */

public class VoiceOfSeren extends Command {

    public String getName(){
        return "vos";
    }

    public void run(ChatMessage msg, List<Command> commands) throws ConnectionException {

        String activeVos = "";
        String clan1 = "";
        String clan2 = "";
        String apiUrl = "https://fdcvos.herokuapp.com/api/tweets/findOne?filter={%22order%22:%20%22timestamp_ms%20DESC%22}";

        String json = CommonUtilities.getTextFromUrl(apiUrl);

        Gson gson = new Gson();

        VOS v = gson.fromJson(json, VOS.class);

        clan1 = v.getText().split(" ")[9];
        clan2 = v.getText().split(" ")[11];

        msg.getChat().sendMessage(Message.create()
                .with(Text.rich("Current VoS: "))
                .with(Text.rich(clan1 + " & " + clan2).withBold()));

    }

}
