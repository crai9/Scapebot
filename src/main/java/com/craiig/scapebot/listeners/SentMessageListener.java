package com.craiig.scapebot.listeners;

import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.events.EventHandler;
import com.samczsun.skype4j.events.Listener;
import com.samczsun.skype4j.events.chat.message.MessageSentEvent;

import static com.craiig.scapebot.utilities.CommonUtilities.logSentMessage;
import static com.craiig.scapebot.utilities.CommonUtilities.participantToUser;


public class SentMessageListener implements Listener {

    private final Skype skype;

    public SentMessageListener(Skype skype){
        this.skype = skype;
    }

    @EventHandler
    public void onMessage(MessageSentEvent e) {

        logSentMessage(e.getMessage().getContent().asPlaintext(), participantToUser(e.getMessage().getSender()).getUsername());

    }


}
