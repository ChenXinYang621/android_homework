package com.example.recipelist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private final LinkedList<String> mMenuList = new LinkedList<>();

    private final LinkedList<String> mContentList = new LinkedList<>();

    private final LinkedList<String> mRecipeList = new LinkedList<>();

    private final LinkedList<Integer> mPhotoList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = findViewById(R.id.recyclerview);
        mMenuList.add("Donut");
        mMenuList.add("Froyo");
        mMenuList.add("IceCream");
        mContentList.add("Donuts are glazed and sprinkled with candy.");
        mContentList.add("Froyo is premium self-serve frozen yogurt.");
        mContentList.add("Ice cream sandwiches have chocolate wafers and vanilla filling.");

        MenuListAdapter mAdapter = new MenuListAdapter(this, mMenuList, mContentList, mRecipeList, mPhotoList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecipeList.add(getString(R.string.donut_details));
        mRecipeList.add(getString(R.string.froyo_details));
        mRecipeList.add(getString(R.string.ice_cream_details));
        mPhotoList.add(R.drawable.donut_circle);
        mPhotoList.add(R.drawable.froyo_circle);
        mPhotoList.add(R.drawable.icecream_circle);

    }
}