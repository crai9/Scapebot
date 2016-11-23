package com.craiig.scapebot.utilities;

/**
 * Created by craig on 04/10/2015, 13:54.

 */

import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.participants.Participant;
import com.samczsun.skype4j.participants.User;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CommonUtilities {

    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";


    public static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static String getTextFromUrl(String site) {

        try {

            URL url = new URL(site);

            return convertStreamToString(url.openStream());


        }
        catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        }
        return "Not available";

    }

    public static String msToHMS(long millis){

        String hms = "";
        int h,m,s;

        h = (int) TimeUnit.MILLISECONDS.toHours(millis);
        m = (int) TimeUnit.MILLISECONDS.toMinutes(millis) -  (int) TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));
        s = (int) TimeUnit.MILLISECONDS.toSeconds(millis) - (int) TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));

        if(h > 0){
            hms += h + "h ";
        }
        if(m > 0){
            hms += m + "m ";
        }
        if(s > 0){
            hms += s + "s";
        }

        return hms;

    }

    public static String getRSN(String username, ChatMessage message, String trigger){

        if(message.getContent().asPlaintext().length() > trigger.length() + 2){
            return message.getContent().asPlaintext().replace("!" + trigger + " ", "");
        }

        ArrayList<String> existing = FileUtilities.readTextFile("data/rsn.txt");
        HashMap<String, String> pairs = new HashMap();

        if(existing.size() < 1){
            return null;
        }

        for(String pair : existing){

            String[] split = pair.split(",");
            pairs.put(split[0], split[1]);

        }
        if(pairs.containsKey(username)){
            return pairs.get(username);
        }

        return null;
    }

    public static void logMessage(String msg, String sender){
        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss");
        Date date = new Date();
        System.out.println(ANSI_GREEN + "[" + sdf.format(date) + " ~ " + sender + "] " + ANSI_RESET + msg);
    }

    public static void logSentMessage(String msg, String sender){
        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss");
        Date date = new Date();
        System.out.println(ANSI_RED + "[" + sdf.format(date) + " ~ " + sender + "] " + ANSI_RESET + msg);
    }

    public static String parseXP(String line){

        String[] sections = line.split("XP");
        try {

            String formatted = NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(sections[0]));
            return formatted + "XP" + sections[1];

        } catch (NumberFormatException e) {
            return line;
        }
    }

    public static User participantToUser(Participant participant){
        return (User) participant;
    }

}
