package com.example.orderplatform.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.orderplatform.R
import com.example.orderplatform.adapter.DinnerAdapter
import com.example.orderplatform.entity.Product
import com.example.orderplatform.utils.BaseParcelable

class ProductFragment() : Fragment() {
    var mView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("show_test", "create complete")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("show_test", "open the view")
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_product, container, false)
        initRecyclerView()
        return mView
    }

    private fun initRecyclerView() {
        val any = arguments?.getParcelable<BaseParcelable>("product")?.value
        val list = any as List<Product>

        val mTitle = mutableListOf<String>()
        val mPrice = mutableListOf<Int>()
        val mDescription = mutableListOf<Int>()
        val mPicture = mutableListOf<Int>()

        for (product in list) {
            mTitle.add(0, product.name)
            mPrice.add(0, product.price)
            mDescription.add(0, product.word)
            mPicture.add(0, product.picture)
            Log.d("show_test", "hava the fragment ${product.name}")
        }

        val mRecyclerView: RecyclerView = mView?.findViewById(R.id.recyclerview)!!

        val mAdapter = context?.let { DinnerAdapter(it, mTitle, mPrice, mDescription, mPicture) }
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager =
            LinearLayoutManager(context)
    }
}