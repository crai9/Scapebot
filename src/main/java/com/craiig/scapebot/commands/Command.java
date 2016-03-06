package com.craiig.scapebot.commands;

import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;

/**
 * Created by Craig on 02/03/2016, 00:41, 01:17.

 */
public abstract class Command {

    public abstract String getName();

    public abstract void run(ChatMessage msg) throws ConnectionException;

}
