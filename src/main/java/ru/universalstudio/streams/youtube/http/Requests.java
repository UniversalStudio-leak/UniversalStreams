package ru.universalstudio.streams.youtube.http;

import java.io.*;
import java.net.*;

/**
 * @Author source code: NaulbiMIX
 * @Author plugin code: UniversalStudio
 */

public class Requests {

    public static String post_upload(String string, File file) {
        StringBuilder post = new StringBuilder();
        try {
            MultipartUtility multipartUtility = new MultipartUtility(string, "utf-8");
            multipartUtility.addFilePart("file", file);
            for(String s : multipartUtility.finish()) {
                post.append(s);
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return post.toString();
    }

    public static String post(String link, String writer) {
        StringBuilder post = new StringBuilder();
        try {
            URLConnection URL = (new URL(link)).openConnection();
            URL.setDoOutput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(URL.getOutputStream());
            outputStreamWriter.write(writer);
            outputStreamWriter.flush();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(URL.getInputStream()));
            String bufferedReaderValue = bufferedReader.readLine();
            if(bufferedReaderValue != null) { // ладна
                post.append(bufferedReaderValue);
            }
            outputStreamWriter.close();
            bufferedReader.close();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return post.toString();
    }

    public static String get(String link) {
        StringBuilder get = new StringBuilder();
        try {
            HttpURLConnection URL = (HttpURLConnection) (new URL(link)).openConnection();
            URL.setDoOutput(true);
            URL.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
            URL.setRequestProperty("Accept", "*/*");
            InputStream inputStream = (URL.getResponseCode() >= 400) ? URL.getErrorStream() : URL.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String bufferedReaderValue = bufferedReader.readLine();
            if(bufferedReaderValue != null) {
                get.append(bufferedReaderValue);
            }
            bufferedReader.close();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return get.toString();
    }
}
