package com.example.orderplatform.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.orderplatform.R
import com.example.orderplatform.adapter.ProductAdapter
import com.example.orderplatform.database.MDataBaseHelper
import com.example.orderplatform.database.ProductDao

class ProductFragment() : Fragment() {

    private var mView: View? = null

    private var mHelper: MDataBaseHelper? = null

    private var productDao: ProductDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHelper = context?.let { MDataBaseHelper(it) }
        productDao = ProductDao(mHelper!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_product, container, false)
        initRecyclerView()
        return mView
    }

    private fun initRecyclerView() {
        val kind = arguments?.getInt("kind")
        val productList = productDao!!.findProductByCatalogue(kind!!)

        val mRecyclerView: RecyclerView = mView?.findViewById(R.id.recyclerview)!!
        val mAdapter = context?.let { ProductAdapter(it, productList) }
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(context)
    }
}