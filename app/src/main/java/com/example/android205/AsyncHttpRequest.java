package com.example.android205;

import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class AsyncHttpRequest implements Runnable {

    private MainActivity mainActivity;
    private String urlStr;
    private String resStr;
    private Handler handler;
    public AsyncHttpRequest(Handler handler, MainActivity mainActivity, String urlStr) {
        this.handler = handler;
        this.mainActivity = mainActivity;
        this.urlStr = urlStr;

    }

    @Override
    public void run() {
        Log.i("AsyncRequest", "BackgroundTask start...");


        resStr = "取得に失敗しました。";
        HttpsURLConnection connection = null;
        try {
            URL url = new URL(urlStr);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            resStr = inputStreamToString(connection.getInputStream());
        } catch (Exception e){
            e.printStackTrace();
            Log.e("AsyncHttpRequest", e.toString());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                onPostExecute();
            }
        });
    }

    private String inputStreamToString(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }
    private void onPostExecute() {
        Log.i("AsyncRequest", "onPostExecute start..");
        //after execute

        TextView textView = mainActivity.findViewById(R.id.txtContents);
        textView.setText(resStr);
    }
}
