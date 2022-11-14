package com.example.toastchecked;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String[] messages = new String[5];

    private final boolean[] checked = new boolean[5];

    private final HashMap<Integer, Integer> hashMap = new HashMap<>(5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.apple).setOnClickListener(this);
        findViewById(R.id.orange).setOnClickListener(this);
        findViewById(R.id.pear).setOnClickListener(this);
        findViewById(R.id.strawberry).setOnClickListener(this);
        findViewById(R.id.peach).setOnClickListener(this);
        findViewById(R.id.show_button).setOnClickListener(this);

        messages[0] = getString(R.string.apple);
        messages[1] = getString(R.string.orange);
        messages[2] = getString(R.string.pear);
        messages[3] = getString(R.string.strawberry);
        messages[4] = getString(R.string.peach);

        Arrays.fill(checked, false);

        hashMap.put(R.id.apple, 0);
        hashMap.put(R.id.orange, 1);
        hashMap.put(R.id.pear, 2);
        hashMap.put(R.id.strawberry, 3);
        hashMap.put(R.id.peach, 4);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.show_button) {
            displayToastMessage(checkShow());
        } else {
            boolean isChecked = ((CheckBox) v).isChecked();
            checked[hashMap.get(id)] = isChecked;
        }
    }

    public String checkShow() {
        StringBuilder builder = new StringBuilder();
        builder.append("Topping:");
        for (int i = 0; i < checked.length; i++) {
            if (checked[i]) {
                builder.append(" ").append(messages[i]);
            }
        }

        return builder.toString();
    }

    public void displayToastMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}