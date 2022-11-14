package com.example.tabexperiment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PagerAdapter extends FragmentStateAdapter {
    private final int itemCount;

    public PagerAdapter(@NonNull FragmentManager fragmentManager,
                        @NonNull Lifecycle lifecycle,
                        int itemCount) {
        super(fragmentManager, lifecycle);
        this.itemCount = itemCount;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new TabFragment1();
            case 1:
                return new TabFragment2();
            case 2:
                return new TabFragment3();
        }
        return new TabFragment1();
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }
}
