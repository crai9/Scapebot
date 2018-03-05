package com.craiig.scapebot;

import com.craiig.scapebot.listeners.ChatListener;
import com.craiig.scapebot.listeners.CommandListener;
import com.craiig.scapebot.listeners.ContactRequestListener;
import com.craiig.scapebot.listeners.SentMessageListener;
import com.craiig.scapebot.timers.RSSChecker;
import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.SkypeBuilder;

import com.craiig.scapebot.utilities.FileUtilities;
import com.samczsun.skype4j.Visibility;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.exceptions.InvalidCredentialsException;
import com.samczsun.skype4j.exceptions.NotParticipatingException;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.craiig.scapebot.utilities.CommonUtilities.logSysMessage;

class Main {

    public static void main(String[] args) {

        FileUtilities.emptyFile("data/uptime.txt");
        FileUtilities.writeToTextFile("data/", "uptime.txt",new Date().getTime() + "");
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

                        logSysMessage("Logged in to new instance", "System");

                        newSkype.getEventDispatcher().registerListener(new ChatListener(newSkype));
                        newSkype.getEventDispatcher().registerListener(new SentMessageListener(newSkype));
                        newSkype.getEventDispatcher().registerListener(new CommandListener(newSkype));
                        newSkype.getEventDispatcher().registerListener(new ContactRequestListener(newSkype));

                        try{
                            newSkype.subscribe();
                        }catch (IllegalStateException ex){
                            logSysMessage("Failed to subscribe...", "System");
                        }

                        newSkype.setVisibility(Visibility.ONLINE);

                        RSSChecker rss = new RSSChecker(newSkype);
                        rss.start();

                        skype = newSkype;

                        if (oldSkype != null) {
                            try {
                                oldSkype.logout();
                                logSysMessage("Logged out previous instance", "System");
                                oldSkype = null;
                                System.gc();
                            } catch (Exception ex) {
                                logSysMessage("Error when logging out of previous instance", "System");
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
