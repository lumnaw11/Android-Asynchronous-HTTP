package com.example.android205;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        executorService = Executors.newSingleThreadExecutor();


        Looper mainLooper = getMainLooper();
        Handler handler = HandlerCompat.createAsync(mainLooper);

        Button btn = findViewById(R.id.btnGet);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                Uri.Builder uriBuilder = new Uri.Builder();
                uriBuilder.scheme("https");
                uriBuilder.authority("ja.wikipedia.org");
                uriBuilder.path("/w/api.php");

                uriBuilder.appendQueryParameter("export", "");
                uriBuilder.appendQueryParameter("action", "query");
                uriBuilder.appendQueryParameter("format", "xml");
                uriBuilder.appendQueryParameter("titles", "java");

                AsyncHttpRequest asyncHttpRequest = new AsyncHttpRequest(handler, MainActivity.this, uriBuilder.toString());
                executorService.submit(asyncHttpRequest);
            }
        });
    }
}