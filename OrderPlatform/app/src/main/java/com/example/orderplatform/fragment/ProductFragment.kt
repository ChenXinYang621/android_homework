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
import com.example.orderplatform.adapter.ProductAdapter
import com.example.orderplatform.entity.Product
import com.example.orderplatform.utils.BaseParcelable

class ProductFragment() : Fragment() {
    private var mView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        }

        val mRecyclerView: RecyclerView = mView?.findViewById(R.id.recyclerview)!!

        val mAdapter = context?.let { ProductAdapter(it, mTitle, mPrice, mDescription, mPicture) }
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager =
            LinearLayoutManager(context)
    }
}