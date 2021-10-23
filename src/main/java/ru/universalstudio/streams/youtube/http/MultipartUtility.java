package ru.universalstudio.streams.youtube.http;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * @Author source code: NaulbiMIX
 * @Author plugin code: UniversalStudio
 */

public class MultipartUtility {

    private String charset;
    private PrintWriter writer;
    private String boundary;
    private HttpURLConnection httpConn;
    private OutputStream outputStream;
    private static final String LINE_FEED = "\r\n";

    public MultipartUtility(String string, String string2) throws IOException {
        this.charset = string2;
        this.boundary = "===" + System.currentTimeMillis() + "===";
        this.httpConn = (HttpURLConnection)new URL(string).openConnection();
        this.httpConn.setUseCaches(false);
        this.httpConn.setDoOutput(true);
        this.httpConn.setDoInput(true);
        this.httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + this.boundary);
        this.httpConn.setRequestProperty("User-Agent", "CodeJava Agent");
        this.httpConn.setRequestProperty("Test", "Bonjour");
        this.outputStream = this.httpConn.getOutputStream();
        this.writer = new PrintWriter(new OutputStreamWriter(this.outputStream, string2), true);
    }

    public void addFormField(String string, String string2) {
        this.writer.append(("--" + this.boundary)).append(LINE_FEED);
        this.writer.append(("Content-Disposition: form-data; name=\"" + string + "\"")).append(LINE_FEED);
        this.writer.append(("Content-Type: text/plain; charset=" + this.charset)).append(LINE_FEED);
        this.writer.append(LINE_FEED);
        this.writer.append(string2).append(LINE_FEED);
        this.writer.flush();
    }

    public void addFilePart(String string, File file) throws IOException {
        int n;
        String string2 = file.getName();
        this.writer.append(("--" + this.boundary)).append(LINE_FEED);
        this.writer.append(("Content-Disposition: form-data; name=\"" + string + "\"; filename=\"" + string2 + "\"")).append(LINE_FEED);
        this.writer.append(("Content-Type: " + URLConnection.guessContentTypeFromName(string2))).append(LINE_FEED);
        this.writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
        this.writer.append(LINE_FEED);
        this.writer.flush();
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] arrby = new byte[4096];
        while ((n = fileInputStream.read(arrby)) != -1) {
            this.outputStream.write(arrby, 0, n);
        }
        this.outputStream.flush();
        fileInputStream.close();
        this.writer.append(LINE_FEED);
        this.writer.flush();
    }

    public void addHeaderField(String string, String string2) {
        this.writer.append((string + ": " + string2)).append(LINE_FEED);
        this.writer.flush();
    }

    public List<String> finish() throws IOException {
        List<String> list = new ArrayList<>();
        this.writer.append(LINE_FEED).flush();
        this.writer.append(("--" + this.boundary + "--")).append(LINE_FEED);
        this.writer.close();
        int n = this.httpConn.getResponseCode();
        if (n == 200) {
            String string;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.httpConn.getInputStream()));
            while ((string = bufferedReader.readLine()) != null) {
                list.add(string);
            }
            bufferedReader.close();
            this.httpConn.disconnect();
            return list;
        }
        throw new IOException("Server returned non-OK status: " + n);
    }
}
