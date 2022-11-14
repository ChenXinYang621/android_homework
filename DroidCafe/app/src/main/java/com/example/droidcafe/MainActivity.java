package com.example.droidcafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.donut).setOnClickListener(this);
        findViewById(R.id.ice_cream).setOnClickListener(this);
        findViewById(R.id.froyo).setOnClickListener(this);
        findViewById(R.id.fab).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.donut) {
            // 通过 getString 可以获得 id 对应的字符串内容
            description = getString(R.string.donut_order_message);
            displayToast(description);
        } else if (id == R.id.ice_cream) {
            description = getString(R.string.ice_cream_order_message);
            displayToast(description);
        } else if (id == R.id.froyo) {
            description = getString(R.string.froyo_order_message);
            displayToast(description);
        } else if (id == R.id.fab) {
            Intent intent = new Intent(MainActivity.this, OrderActivity.class);
            if (description == null) {
                description = "you haven't chosen anything";
            }
            Bundle bundle = new Bundle();
            bundle.putString("description", description);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}