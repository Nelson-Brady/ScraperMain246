package com.brady.scrapermain;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/***************************************************************
 * ReceiveData
 *
 * AsyncTask executed by InputCode.  Performs a POST request to
 * the specified URL using the variables passed in from InputCode.
 * OnPostExecute then passes the raw response string back to InputCode where
 * it will create a DisplayCode with the string.
 **************************************************************/
public class ReceiveData extends AsyncTask<URL, Integer, Long> {

    private String response = "";
    private String SinceTime;
    private String GoesAddress;
    private Context myContext;

    // Non-Default Constructor
    ReceiveData(Context context, String since, String goes) {
        this.myContext = context;
        SinceTime = since;
        GoesAddress = goes;
        }

    // Appends the query string prior to posting
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder feedback = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                feedback.append("&");

            feedback.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            feedback.append("=");
            feedback.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return feedback.toString();
    }

    public void getData() throws IOException {

        HashMap<String, String> params = new HashMap<>();
        params.put("DCPID", GoesAddress);
        params.put("SINCE", SinceTime);

        URL url = new URL("https://eddn.usgs.gov/cgi-bin/fieldtest.pl");
        HttpURLConnection client = null;
        try {
            client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("POST");
            client.setRequestProperty("multipart/form-data", "https://eddn.usgs.gov/fieldtest.html;charset=UTF-8");
            client.setDoInput(true);
            client.setDoOutput(true);

            OutputStream os = client.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(params));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = client.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            }
            else {
                response = "";
            }
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        finally {
            if (client != null) // Make sure the connection is not null.
                client.disconnect();
        }
    }

    @Override
    protected Long doInBackground(URL... params) {
        try {
            getData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // This counts how many bytes were downloaded
        final byte[] result = response.getBytes();
        Long numOfBytes = Long.valueOf(result.length);
        return numOfBytes;
    }

    protected void onPostExecute(Long result) {
        System.out.println("Downloaded " + result + " bytes");
        System.out.println(response);
        // This is just printing it to the console for now.
        // We will pass the string elsewhere and decode it.
        InputCode input = new InputCode();
        input.passToDisplay(myContext, response);
    }
}
