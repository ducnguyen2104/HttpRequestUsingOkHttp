package com.example.ducnguyenvan.httprequestusingokhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView txt;
    private Button btn;
    private ProgressBar prgBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = (TextView)findViewById(R.id.text);
        txt.setMovementMethod(new ScrollingMovementMethod());
        btn = (Button)findViewById(R.id.button);
        prgBar = (ProgressBar)findViewById(R.id.prgBar);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestThenPrintResponse();
                prgBar.setVisibility(View.VISIBLE);
                setTitle("Loading...");

            }
        });
    }

    private void sendRequestThenPrintResponse() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://jsonplaceholder.typicode.com/photos").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                txt.setText("Error");
                prgBar.setVisibility(View.GONE);
                setTitle("Error");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseTxt  = response.body().string();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txt.setText(responseTxt);
                            prgBar.setVisibility(View.GONE);
                            setTitle("Loading finished");
                        }
                    });
                }
            }
        });
    }
}
