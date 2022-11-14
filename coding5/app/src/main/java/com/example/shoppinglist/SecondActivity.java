package com.example.shoppinglist;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String LOG_TAG = SecondActivity.class.getSimpleName();

    private Intent replyIntent;

    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        findViewById(R.id.button_back).setOnClickListener(this);
        findViewById(R.id.button_item1).setOnClickListener(this);
        findViewById(R.id.button_item2).setOnClickListener(this);
        findViewById(R.id.button_item3).setOnClickListener(this);
        findViewById(R.id.button_item4).setOnClickListener(this);
        findViewById(R.id.button_item5).setOnClickListener(this);
        findViewById(R.id.button_item6).setOnClickListener(this);
        findViewById(R.id.button_item7).setOnClickListener(this);
        findViewById(R.id.button_item8).setOnClickListener(this);
        findViewById(R.id.button_item9).setOnClickListener(this);
        findViewById(R.id.button_item10).setOnClickListener(this);

        replyIntent = new Intent();
        bundle = new Bundle();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.button_back != id) {
            bundle.putInt("item", id);
            replyIntent.putExtras(bundle);
        }
        setResult(Activity.RESULT_OK, replyIntent);
        finish();
    }
}