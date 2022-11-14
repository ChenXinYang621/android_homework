package com.example.simpleasynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTextView;

    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textView1);
        findViewById(R.id.start_button).setOnClickListener(this);
        executorService = Executors.newFixedThreadPool(1);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.start_button) {
            doAsyncCode();
        }
    }

    private void doAsyncCode() {
        executorService.submit(() -> {
            mTextView.setText(getString(R.string.thread_wait));
            Random r = new Random();
            int n = r.nextInt(11);
            int s = n * 200;
            try {
                Thread.sleep(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            doOnUiCode(s);
        });
    }

    private void doOnUiCode(int s) {
        Handler uiThread = new Handler(Looper.getMainLooper());
        uiThread.post(new Runnable() {
            @Override
            public void run() {
                // 更新你的 UI

                mTextView.setText("Awake at last after sleeping for " + s + " milliseconds");
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}