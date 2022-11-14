package com.example.shoppinglist;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ActivityResultLauncher<Intent> intentActivityResultLauncher;

    private static long[] array = new long[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_jump).setOnClickListener(this);

        intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Intent data = result.getData();
                    int resultCode = result.getResultCode();
                    if (data != null && resultCode == RESULT_OK) {
                        Bundle bundle = data.getExtras();
                        int id = bundle.getInt("item");
                        if (id == R.id.button_item1) {
                            findViewById(R.id.shop_item1).setVisibility(View.VISIBLE);
                            array[0] = R.id.shop_item1;
                        } else if (id == R.id.button_item2) {
                            findViewById(R.id.shop_item2).setVisibility(View.VISIBLE);
                            array[1] = R.id.shop_item2;
                        } else if (id == R.id.button_item3) {
                            findViewById(R.id.shop_item3).setVisibility(View.VISIBLE);
                            array[2] = R.id.shop_item3;
                        } else if (id == R.id.button_item4) {
                            findViewById(R.id.shop_item4).setVisibility(View.VISIBLE);
                            array[3] = R.id.shop_item4;
                        } else if (id == R.id.button_item5) {
                            findViewById(R.id.shop_item5).setVisibility(View.VISIBLE);
                            array[4] = R.id.shop_item5;
                        } else if (id == R.id.button_item6) {
                            findViewById(R.id.shop_item6).setVisibility(View.VISIBLE);
                            array[5] = R.id.shop_item6;
                        } else if (id == R.id.button_item7) {
                            findViewById(R.id.shop_item7).setVisibility(View.VISIBLE);
                            array[6] = R.id.shop_item7;
                        } else if (id == R.id.button_item8) {
                            findViewById(R.id.shop_item8).setVisibility(View.VISIBLE);
                            array[7] = R.id.shop_item8;
                        } else if (id == R.id.button_item9) {
                            findViewById(R.id.shop_item9).setVisibility(View.VISIBLE);
                            array[8] = R.id.shop_item9;
                        } else if (id == R.id.button_item10) {
                            findViewById(R.id.shop_item10).setVisibility(View.VISIBLE);
                            array[9] = R.id.shop_item10;
                        }
                    }
                });

        if (savedInstanceState != null) {
            array = savedInstanceState.getLongArray("item_cache");
            for (long id : array) {
                System.out.println(id);
            }
            for (long id : array) {
                if (findViewById((int) id) != null) {
                    findViewById((int) id).setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, SecondActivity.class);
        intentActivityResultLauncher.launch(intent);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        for (long id : array) {
            System.out.println(id);
        }
        outState.putLongArray("item_cache", array);
    }
}