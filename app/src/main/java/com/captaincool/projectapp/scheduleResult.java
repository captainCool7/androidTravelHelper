package com.captaincool.projectapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class scheduleResult extends AppCompatActivity {
    final static String TAG = "myapp";
    TextView resultView;
    public class DownloadTask extends AsyncTask<String, Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i(TAG,"Result is "+s);
            resultView.setText(s);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_result);
        resultView = findViewById(R.id.resultView);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        Log.i(TAG,"Url is "+ url);
//        Log.i(TAG,"Url is "+ intent.getStringExtra("url"));
        try {
            DownloadTask task = new DownloadTask();
            String returnResult = String.valueOf(task.execute(url));
        }
        catch (Exception e)
        {
            Log.i(TAG,"Error is "+e);
        }
    }
}
