package com.example.rasna.needforspeed;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends Activity {
    protected static final String TAG = "WeatherForecast";
    WebView mWebView;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
        mWebView = (WebView) findViewById(R.id.webView_content);
        mWebView.getSettings().setJavaScriptEnabled(true);
        new SimpleTask().execute("https://www.carhelpcanada.com");
    }

    public boolean isFileExists(String fileName){
        File file = getBaseContext().getFileStreamPath(fileName);
        Log.i(TAG, file.toString());

        return file.exists();

    }

    public Bitmap getImage(URL url){
        Log.i(TAG, "In getImage");
        HttpURLConnection connection = null;
        try{
            connection = (HttpURLConnection)url.openConnection();
            int responseCode = connection.getResponseCode();
            if(responseCode == 200){
                Log.i(TAG, "downloading image");
                Bitmap bm = BitmapFactory.decodeStream(connection.getInputStream());
                return bm;
            }
            else{
                return null;
            }

        }catch(MalformedURLException e){
            e.printStackTrace();
            return null;
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }
    }

    private class SimpleTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // Create Show ProgressBar
        }

        protected String doInBackground(String... urls)   {
            String result = "";
            try {

                URL dataUrl = new URL(urls[0]);

                HttpURLConnection connection = (HttpURLConnection)dataUrl.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("GET");
                connection.connect();

                int status = connection.getResponseCode();
                Log.d("connection", "status " + status);


                if (status == 200) {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader
                            (new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result += line;
                    }

                    progressBar.setVisibility(View.GONE);
                }

            } catch (Exception e) {
                Log.i("ddd",e.toString());
            }
            return result;
        }

        protected void onPostExecute(String result)  {
            // Dismiss ProgressBar
            updateWebView(result);
        }
    }

    private void updateWebView(String result) {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadData(result, "text/html; charset=utf-8", "utf-8");
    }



}