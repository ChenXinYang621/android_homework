package com.example.orderplatform.adapter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.orderplatform.database.MDataBaseHelper
import com.example.orderplatform.database.OrderDao
import com.example.orderplatform.database.ProductDao
import com.example.orderplatform.fragment.ProductFragment
import com.example.orderplatform.fragment.InfoFragment
import com.example.orderplatform.fragment.OrderFragment
import com.example.orderplatform.utils.BaseParcelable

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
        val mHelper = MDataBaseHelper(context)
        when (position) {
            0, 1, 2, 3 -> {
                val productDao = ProductDao(mHelper)
                val bundle = Bundle()
                val productFragment = ProductFragment()
                val productList = productDao.findProductByCatalogue(position)
                bundle.putParcelable("product", BaseParcelable(productList))
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