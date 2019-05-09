package com.example.growingmobilef1;

import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class ApiRequestHelper implements Runnable{

    private Context mAppContext;
    private String mUrl;

    public ApiRequestHelper(Context aContext, String aUrl){
        this.mAppContext = aContext;
        this.mUrl = aUrl;
    }

    @Override
    public void run() {
        getContentFromUrl(mUrl);
    }

    public void getContentFromUrl(String aUrl) {
        StringBuilder vStringBuilder = new StringBuilder();
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
            }else{
                throw new IOException(vUrlConnection.getResponseMessage());
            }

        } catch(StackOverflowError | Exception s){
            s.printStackTrace();
        } catch(Error e){
            e.printStackTrace();
        }
    }

}
