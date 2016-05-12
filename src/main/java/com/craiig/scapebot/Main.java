package com.craiig.scapebot;

import static com.craiig.scapebot.utilities.CommonUtilities.log;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Craig on 01/03/2016, 16:03.

 */

class Main {

    public static void main(String[] args) {

        runBot();

    }

    public static void runBot(){

        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(new Runnable() {

            Skype skype = null;

            @Override
            public void run() {
                try {

                    FileUtilities.directoryExists("data");
                    ArrayList<String> loginDetails = FileUtilities.readTextFile("data/login.txt");

                    Skype oldSkype = skype;
                    Skype newSkype = new SkypeBuilder(loginDetails.get(0), loginDetails.get(1)).withAllResources().build();

                    try{

                        newSkype.login();

                        log("Logged in");

                        newSkype.getEventDispatcher().registerListener(new ChatListener(newSkype));
                        newSkype.getEventDispatcher().registerListener(new CommandListener(newSkype));

                        try{
                            newSkype.subscribe();
                        }catch (IllegalStateException ex){
                            System.out.println("Failed to subscribe...");
                        }

                        newSkype.setVisibility(Visibility.ONLINE);

                        RSSChecker rss = new RSSChecker(newSkype);
                        rss.start();

                        skype = newSkype;

                        if (oldSkype != null) {
                            try {
                                oldSkype.logout();
                                log("Logged out previous instance");
                                oldSkype = null;
                                System.gc();
                            } catch (Exception ex) {
                                System.out.println("Error when logging out of previous instance");
                                ex.printStackTrace();
                            }
                        }

                    } catch (ConnectionException | InvalidCredentialsException | NotParticipatingException ex){
                        System.err.println("An error occurred: " + ex.getMessage());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 30, TimeUnit.MINUTES);

    }

}
