package com.apareciumlabs.brionsilva.safeplant;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by brionsilva on 22/01/2017.
 */


class UpdateThingspeakTask extends AsyncTask<Void, Void, String> {

    private Exception exception;

    String urlNew;

    public UpdateThingspeakTask(String urlNew){
        this.urlNew = urlNew;
    }
    protected void onPreExecute() {
    }

    protected String doInBackground(Void... urls) {
        try {
            URL url = new URL(urlNew);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(String response) {
        // We completely ignore the response
        // Ideally we should confirm that our update was successful
    }
}