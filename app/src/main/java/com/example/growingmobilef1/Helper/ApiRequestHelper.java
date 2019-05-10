package com.example.growingmobilef1.Helper;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class ApiRequestHelper implements Runnable{

    private String mUrl;
    public ApiRequestHelper(String aUrl){
        this.mUrl = aUrl;
    }

    @Override
    public void run() {
         getContentFromUrl(mUrl);
    }

    // Function to do the api request and return a JSON object with the response
    // Parse the JSON with a custom Helper class in the fragment
    public JSONObject getContentFromUrl(String aUrl) {
        StringBuilder vStringBuilder = new StringBuilder();
        JSONObject vResponseJsonObject = new JSONObject();
        try {

            URL vUrl = new URL(aUrl);
            HttpURLConnection vUrlConnection = (HttpURLConnection) vUrl.openConnection();
            if (vUrlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream vInputStream = vUrlConnection.getInputStream();
                BufferedReader vBufferedReader = new BufferedReader(new InputStreamReader(vInputStream, Charset.forName("UTF-8")));
                String vLine;
                while ((vLine = vBufferedReader.readLine()) != null) {
                    vStringBuilder.append(vLine).append("\n");
                }
                vUrlConnection.disconnect();

                try {
                    // The API response must be parsed multiple times to isolate the races
                    vResponseJsonObject = new JSONObject(String.valueOf(vStringBuilder));

                } catch (JSONException e) {
                    Log.e("JSON Parser", "Error parsing data " + e.toString());
                }
            }else{
                throw new IOException(vUrlConnection.getResponseMessage());
            }
        } catch(StackOverflowError | Exception s){
            s.printStackTrace();
        } catch(Error e){
            e.printStackTrace();
        }

        return vResponseJsonObject;
    }
}
