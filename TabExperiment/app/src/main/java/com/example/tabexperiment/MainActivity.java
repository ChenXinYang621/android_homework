package com.example.tabexperiment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class MainActivity extends AppCompatActivity {
    private final String[] title = {"Top Stories", "Tech News", "Cooking"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(title[0]));
        tabLayout.addTab(tabLayout.newTab().setText(title[1]));
        tabLayout.addTab(tabLayout.newTab().setText(title[2]));

        final ViewPager2 viewPager = findViewById(R.id.pager);
        // 通过 getLifecycle() 获取生命周期
        final PagerAdapter adapter = new PagerAdapter(
                getSupportFragmentManager(),
                getLifecycle(),
                tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        // 解决不同步问题
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(title[position])
        ).attach();
    }
}