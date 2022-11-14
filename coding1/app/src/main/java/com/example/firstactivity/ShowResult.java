package com.example.firstactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ShowResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);
        TextView textView = findViewById(R.id.receive_count);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String Number = bundle.getString(MainActivity.CountNumber);
        textView.setText(Number);
    }
}