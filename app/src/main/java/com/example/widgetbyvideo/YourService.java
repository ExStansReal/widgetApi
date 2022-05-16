package com.example.widgetbyvideo;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.annotation.Nullable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class YourService {

    String futureJokeString = "";


    public String getJoke() {

        futureJokeString = "";

        new JokeLoader().execute();

        while (futureJokeString.equals("")) {

        }

        return futureJokeString;
    }


    private class JokeLoader extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            String jsonStr = getJson("https://api.chucknorris.io/jokes/random");

            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                futureJokeString = jsonObject.getString("value");


            } catch (JSONException a) {
                a.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            futureJokeString = "";


        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            if (!futureJokeString.equals("")) {

            }

        }

        private String getJson(String link) {
            String data = "";
            try {
                URL url = new URL(link);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                    data = r.readLine();
                    urlConnection.disconnect();
                }

            } catch (IOException a) {
                a.printStackTrace();
            }
            return data;
        }
    }
}



