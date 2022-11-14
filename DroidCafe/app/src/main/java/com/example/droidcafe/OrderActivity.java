package com.example.droidcafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        findViewById(R.id.sameday).setOnClickListener(this);
        findViewById(R.id.nextday).setOnClickListener(this);
        findViewById(R.id.pickup).setOnClickListener(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        TextView show = findViewById(R.id.show_buying);
        show.setText(bundle.getString("description"));
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        boolean checked = ((RadioButton) v).isChecked();
        int id = v.getId();
        if (checked) {
            if (id == R.id.sameday) {
                displayToast(getString(R.string.same_day));
            } else if (id == R.id.nextday) {
                displayToast(getString(R.string.next_day));
            } else if (id == R.id.pickup) {
                displayToast(getString(R.string.pick_up));
            }
        }
    }
}