package com.craiig.scapebot.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Craig on 01/03/2016, 16:02.

 */
public class FileUtilities {

    public static ArrayList<String> readTextFile(String fileName)
    {
        ArrayList<String> result = new ArrayList<>();

        File f = new File(fileName);
        if (!f.exists())
        {
            return result;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            String line = br.readLine();

            while (line != null)
            {
                result.add(line);
                line = br.readLine();
            }
        } catch (IOException e)
        {
            e.printStackTrace();

        }

        return result;
    }

    @SuppressWarnings("UnusedReturnValue")
    public static boolean directoryExists(String directory)
    {
        // Check if the directory exists, if not, create it
        boolean result = false;
        File dirTest = new File(directory);
        if (!dirTest.exists())
        {
            try
            {
                result = dirTest.mkdir();

            } catch (SecurityException e)
            {
                e.printStackTrace();
            }

            // Failed com create directory!
            if (!result)
            {
                System.err.println("failed to create directory");
            }
        } else {
            result = true;
        }

        return result;
    }

}
