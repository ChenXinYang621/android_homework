package com.example.notchange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int mCount = 0;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_count).setOnClickListener(this);
        textView = findViewById(R.id.text_count);

        if (savedInstanceState != null) {
            mCount = savedInstanceState.getInt("save_count");
            textView.setText(String.format(Locale.getDefault(), "%d", mCount));
        }
    }

    @Override
    public void onClick(View v) {
        mCount++;
        if (textView != null) {
            textView.setText(String.format(Locale.getDefault(), "%d", mCount));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("save_count", mCount);
    }
}