package com.brady.scrapermain;

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

/**
 * Created by ryani on 3/10/2017.
 */

public class receiveData extends AsyncTask<URL, Integer, Long> {

    String response = "";

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
        params.put("DCPID","DD231226");
        params.put("SINCE","5");

        URL url = new URL("https://eddn.usgs.gov/cgi-bin/fieldtest.pl");
        HttpURLConnection client = null;
        try {
            client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("POST");
            //client.setRequestProperty("DCPID","DD231226");  I don't think we need these
            //client.setRequestProperty("SINCE","5");
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
            if(client != null) // Make sure the connection is not null.
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
        return null;
    }

    protected void onPostExecute(Long result) {
        System.out.println("Downloaded " + result + " bytes");
        // This is just printing it to the console for now.
        // We can pass the string elsewhere and decode it.
        System.out.println(response);
    }
}
