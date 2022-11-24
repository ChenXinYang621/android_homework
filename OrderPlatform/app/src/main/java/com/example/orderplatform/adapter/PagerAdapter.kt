package com.example.orderplatform.adapter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.orderplatform.database.MDataBaseHelper
import com.example.orderplatform.database.ProductDao
import com.example.orderplatform.fragment.ProductFragment
import com.example.orderplatform.fragment.InfoFragment
import com.example.orderplatform.utils.BaseParcelable

class PagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val itemsCount: Int,
    context: Context
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private val mHelper: MDataBaseHelper = MDataBaseHelper.getInstance(context)

    private val productDao: ProductDao = mHelper.let { ProductDao.getInstance() }

    override fun getItemCount(): Int {
        return itemsCount
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0, 1, 2, 3 -> {
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
        }
        return InfoFragment()
    }
}