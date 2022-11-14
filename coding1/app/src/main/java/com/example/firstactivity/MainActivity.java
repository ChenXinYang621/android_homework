package com.example.firstactivity;

import static com.example.firstactivity.R.id.button_count;
import static com.example.firstactivity.R.id.button_jump;
import static com.example.firstactivity.R.id.button_toast;
import static com.example.firstactivity.R.id.button_zero;
import static com.example.firstactivity.R.id.show_count;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int mCount = 0;

    private TextView showCount;

    public static final String CountNumber = "count_number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 定义使用 activity
        setContentView(R.layout.activity_main);
        showCount = findViewById(show_count);

        findViewById(button_count).setOnClickListener(this);
        findViewById(button_zero).setOnClickListener(this);
        findViewById(button_toast).setOnClickListener(this);
        findViewById(button_jump).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == button_count) {
            mCount++;
            if (showCount != null) {
                showCount.setText(String.format(Locale.getDefault(), "%d", mCount));
            }
        } else if (id == button_zero) {
            mCount = 0;
            showCount.setText(String.format(Locale.getDefault(), "%d", mCount));
        } else if (id == button_toast) {
            Toast.makeText(MainActivity.this, R.string.toast_message, Toast.LENGTH_SHORT).show();
        } else if (id == button_jump) {
            Intent intent = new Intent(this, ShowResult.class);
            Bundle bundle = new Bundle();
            bundle.putString(CountNumber, showCount.getText().toString());
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}