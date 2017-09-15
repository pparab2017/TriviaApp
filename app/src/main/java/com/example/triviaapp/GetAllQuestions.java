package com.example.triviaapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by pushparajparab on 9/22/16.
 */
public class GetAllQuestions extends AsyncTask<String, Void, ArrayList<Question>> {


    GetRequiredMainActivity setQuestions;
    ProgressDialog progressDialog;


    public GetAllQuestions(GetRequiredMainActivity data )
    {
        this.setQuestions = data;
    }
    @Override
    protected ArrayList<Question> doInBackground(String... params) {
        URL url= null;
        ArrayList<Question> toReturn = null;
        try {
             url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = null;


            {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = reader.readLine();
                StringBuilder stringBuilder = new StringBuilder();
                while (line!= null)
                {
                    stringBuilder.append(line);
                    line = reader.readLine();
                }

                try {
                    toReturn = QuestionUtil.parsePerson(stringBuilder.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Context context = setQuestions.getContext();
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading Trivia..");
        progressDialog.setCancelable(false);
        progressDialog.show();


    }

    @Override
    protected void onPostExecute(ArrayList<Question> questions) {
        super.onPostExecute(questions);
        setQuestions.setUpQuestions(questions);
        progressDialog.dismiss();

    }

    @Override
    protected void onProgressUpdate(Void... values) {

        super.onProgressUpdate(values);

    }

    interface GetRequiredMainActivity
    {
        public void setUpQuestions(ArrayList<Question> data);
        public Context getContext();

    }
}
