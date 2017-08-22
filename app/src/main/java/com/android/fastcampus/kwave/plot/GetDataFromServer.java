package com.android.fastcampus.kwave.plot;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by maxx on 2017. 8. 7..
 */

public class GetDataFromServer {

    public static String getData(String url) throws Exception{
        String result = "";
        URL serverUrl = new URL(url);
        HttpURLConnection con = (HttpURLConnection) serverUrl.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        if(responseCode == HttpURLConnection.HTTP_OK){
            String temp = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while((temp = br.readLine()) != null){
                result += temp;
            }
        } else {
            Log.e("Network" , "ErrorCode : " + responseCode);
        }
        return result;
    }
}
