package com.example.zlata.quiz.Server;

import android.os.AsyncTask;
import android.util.Log;

import com.example.zlata.quiz.Quiz;

import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLConnection;

/**
 * Created by User on 10.12.2017.
 */

public class ServerGetAnswersAT extends AsyncTask<Void ,Void ,String>{

    private static final String URLadress="https://quizapplab.herokuapp.com/api/";
    private Quiz quiz;
    public ServerGetAnswersAT(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try
        {
            URL url = new URL (URLadress+"answers/");
            URLConnection conn = url.openConnection();
            BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String s=reader.readLine();
            Log.d("MYTAG", s);
            this.quiz.setRowAnswers(s.split(" "));
            this.quiz.setRowQuestions(s.split(" "));
            return s;
        }
        catch (IOException e) {
        }
        return "problebDoInBackAnswers";
    }

}
