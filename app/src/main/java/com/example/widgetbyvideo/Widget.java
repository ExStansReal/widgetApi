package com.example.widgetbyvideo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Widget extends AppWidgetProvider {

    public static String WIDGET_BUTTON = "MY_PACKAGE_NAME.WIDGET_BUTTON";


    @Override
    public void onUpdate(@NonNull Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews;
        ComponentName watchWidget;

        remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
        watchWidget = new ComponentName(context, Widget.class);

        remoteViews.setOnClickPendingIntent(R.id.button, getPendingSelfIntent(context, WIDGET_BUTTON));
        appWidgetManager.updateAppWidget(watchWidget, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onReceive(context, intent);

        if (WIDGET_BUTTON.equals(intent.getAction())) {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            RemoteViews remoteViews;
            ComponentName watchWidget;

            remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
            watchWidget = new ComponentName(context, Widget.class);


            remoteViews.setTextViewText(R.id.txtJoke, new YourService().getJoke());

            appWidgetManager.updateAppWidget(watchWidget, remoteViews);

        }
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    private String getJson(String link)
    {
        String data = "";
        try {
            URL url = new URL(link);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if(urlConnection.getResponseCode() == HttpsURLConnection.HTTP_OK)
            {
                BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
                data = r.readLine();
                urlConnection.disconnect();
            }

        }
        catch(IOException a)
        {
            a.printStackTrace();
        }
        return data;
    }


}
