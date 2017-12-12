package com.example.zlata.quiz.Server;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class ServerSaveResultAT extends AsyncTask<String ,Void ,String> {

    private static final String URLadress="https://quizapplab.herokuapp.com/api/notes";

    @Override
    protected String doInBackground(String... strings) {
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(URLadress);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            try {
                String name = strings[0];
                String score = strings[1];
                JSONObject obj = new JSONObject();
                obj.put("playerName", name);
                obj.put("score", score);
                wr.writeBytes(obj.toString());
                wr.flush();
                wr.close();
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            urlConnection.connect();
            int response=urlConnection.getResponseCode();
            String respStatus="";
            if(response==200){
                respStatus="Data saved!";
            }else
                respStatus="Problem occurred";
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
