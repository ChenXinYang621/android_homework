package com.example.recipelist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_content);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        TextView title = findViewById(R.id.title_menu);
        title.setText(bundle.getString("title"));
        TextView details = findViewById(R.id.details);
        details.setText(bundle.getString("recipe"));
        ImageView photo = findViewById(R.id.photo);
        photo.setImageResource(bundle.getInt("photo", 1));
    }
}