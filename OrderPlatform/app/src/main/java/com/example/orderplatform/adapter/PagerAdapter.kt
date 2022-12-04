package com.example.orderplatform.adapter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.orderplatform.fragment.InfoFragment
import com.example.orderplatform.fragment.OrderFragment
import com.example.orderplatform.fragment.ProductFragment

class PagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val itemsCount: Int,
    private val context: Context
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return itemsCount
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0, 1, 2, 3 -> {
                val bundle = Bundle()
                val productFragment = ProductFragment()
                bundle.putInt("kind", position)
                productFragment.arguments = bundle
                return productFragment
            }

            4 -> {
                return InfoFragment()
            }

            5 -> {
                return OrderFragment()
            }
        }
        return InfoFragment()
    }
}