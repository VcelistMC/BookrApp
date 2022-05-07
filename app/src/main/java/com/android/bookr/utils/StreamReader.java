package com.android.bookr.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class StreamReader {
    public static String readFromStream(InputStream inputStream){
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return output.toString();
    }
}
