package com.craiig.scapebot.utilities;

/**
 * Created by craig on 04/10/2015, 13:54.

 */

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class CommonUtilities {


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
            log("Malformed URL: " + e.getMessage());
        }
        catch (IOException e) {
            log("I/O Error: " + e.getMessage());
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

    public static String getRSN(String username){

        ArrayList<String> existing = FileUtilities.readTextFile("data/rsn.txt");
        HashMap<String, String> pairs = new HashMap();

        log("Stored before: " + existing.size());
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

    public static void log(String msg){
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        Date date = new Date();
        System.out.println(sdf.format(date) + ": " + msg);
    }
}
