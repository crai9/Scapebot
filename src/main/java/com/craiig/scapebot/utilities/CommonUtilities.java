package com.craiig.scapebot.utilities;

/**
 * Created by craig on 04/10/2015.
 */

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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
            System.out.println("Malformed URL: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        }
        return "Not available";

    }

}
