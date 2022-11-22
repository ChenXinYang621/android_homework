package com.example.orderplatform.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.orderplatform.fragment.DinnerFragment
import com.example.orderplatform.fragment.DrinkFragment
import com.example.orderplatform.fragment.InfoFragment

class PagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, private val itemsCount: Int) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return itemsCount
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return DinnerFragment()
            1 -> return DrinkFragment()

            4 -> return InfoFragment()
        }
        return DinnerFragment()
    }
}