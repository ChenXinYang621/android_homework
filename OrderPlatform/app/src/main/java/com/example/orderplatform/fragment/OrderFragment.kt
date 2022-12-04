package com.example.orderplatform.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.orderplatform.R
import com.example.orderplatform.adapter.OrderAdapter
import com.example.orderplatform.database.MDataBaseHelper
import com.example.orderplatform.database.OrderDao

class OrderFragment : Fragment() {

    private var mView: View? = null

    private var mHelper: MDataBaseHelper? = null

    private var orderDao: OrderDao? = null

    private var register: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mHelper = context?.let { MDataBaseHelper(it) }
        orderDao = OrderDao(mHelper!!)

        register = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            initRecyclerView()
        }
        savedInstanceState?.let {
            initRecyclerView()
        }
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
        val orderList = orderDao!!.findAll()
        val mRecyclerView: RecyclerView = mView?.findViewById(R.id.recyclerview)!!
        val mAdapter = OrderAdapter(requireContext(), orderList, register!!)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(context)
    }
}