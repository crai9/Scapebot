package com.craiig.scapebot;

import com.craiig.scapebot.listeners.ChatListener;
import com.craiig.scapebot.listeners.CommandListener;
import com.craiig.scapebot.timers.RSSChecker;
import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.SkypeBuilder;

import com.craiig.scapebot.utilities.FileUtilities;
import com.samczsun.skype4j.Visibility;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.exceptions.InvalidCredentialsException;
import com.samczsun.skype4j.exceptions.NotParticipatingException;

import java.util.ArrayList;

/**
 * Created by Craig on 01/03/2016, 16:03.

 */

class Main {

    public static void main(String[] args) {

        FileUtilities.directoryExists("data");
        ArrayList<String> loginDetails = FileUtilities.readTextFile("data/login.txt");

        Skype skype = new SkypeBuilder(loginDetails.get(0), loginDetails.get(1)).withAllResources().build();

        try{

            skype.login();

            System.out.println("Logged in");

            skype.getEventDispatcher().registerListener(new ChatListener(skype));
            skype.getEventDispatcher().registerListener(new CommandListener(skype));

            try{
                skype.subscribe();
            }catch (IllegalStateException ex){
                System.out.println("Failed to subscribe...");
            }

            skype.setVisibility(Visibility.AWAY);

            RSSChecker rss = new RSSChecker(skype);
            rss.start();

        } catch (ConnectionException | InvalidCredentialsException | NotParticipatingException ex){
            System.err.println("An error occurred: " + ex.getMessage());
        }

    }

}
