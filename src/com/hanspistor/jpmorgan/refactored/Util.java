package com.hanspistor.jpmorgan.refactored;

import java.io.BufferedReader;
import java.io.IOException;

public class Util {

    // Wrapper to read line from BufferedReader. Mainly just to clean up the loop
    // reading from the Reader
    public static String getLineFromBufferedReader(BufferedReader in) {
        String val = null;
        try {
            val = in.readLine();
        } catch (IOException ioException) {
            System.err.println("There was an error reading the line. Aborting.");
        }

        return val;
    }
}
