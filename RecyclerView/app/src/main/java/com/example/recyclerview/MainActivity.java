package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final LinkedList<String> mWordList = new LinkedList<>();

    private RecyclerView mRecyclerView;

    private WordListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 20; i++) {
            mWordList.addLast(("Word" + i));
        }

        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new WordListAdapter(this, mWordList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.fab).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.recyclerview) {
            int wordListSize = mWordList.size();
            mWordList.addLast("+ Word " + wordListSize);
            Objects.requireNonNull(mRecyclerView.getAdapter()).notifyItemInserted(wordListSize);
            mRecyclerView.smoothScrollToPosition(wordListSize);
        }
    }
}