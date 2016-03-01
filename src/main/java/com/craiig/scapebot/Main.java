package com.craiig.scapebot;

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
        ArrayList<String> details = FileUtilities.readTextFile("data/login.txt");

        Skype skype = new SkypeBuilder(details.get(0), details.get(1)).withAllResources().build();

        try{
            skype.login();

            System.out.println("Logged in");

            skype.subscribe();
            skype.setVisibility(Visibility.ONLINE);

        } catch (ConnectionException | InvalidCredentialsException | NotParticipatingException ex){
            System.err.println("An error occurred: " + ex.getMessage());
        }

    }

}
