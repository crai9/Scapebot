package com.craiig.scapebot.commands;

import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Craig on 09/03/2016, 01:50.
 */
public class FeelsBadMan extends Command {

    public String getName() {
        return "feelsbadman";
    }

    public void run(ChatMessage msg) throws ConnectionException {

        Random rand = new Random();
        int min = 1;
        int max = 20;
        int number = rand.nextInt(max - min + 1) + min;

        String path = (number == 1) ? "img/feelsbadmanLSD.jpg" : "img/feelsbadman.png";
        File feels = new File(path);

        try {
            msg.getChat().sendImage(feels);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}