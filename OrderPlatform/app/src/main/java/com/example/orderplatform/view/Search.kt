package com.example.orderplatform.view

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.orderplatform.R
import com.example.orderplatform.adapter.ProductAdapter
import com.example.orderplatform.database.MDataBaseHelper
import com.example.orderplatform.database.ProductDao

class Search : AppCompatActivity(), OnClickListener {

    private var mHelper: MDataBaseHelper? = null

    private var productDao: ProductDao? = null

    private var foodEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        mHelper = MDataBaseHelper(this)
        productDao = ProductDao(mHelper!!)

        val toolbar: Toolbar = findViewById(R.id.search_toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        foodEditText = findViewById(R.id.search_edit)
        findViewById<Button>(R.id.search_button).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val message = foodEditText!!.text.toString()
        if (message.isNotEmpty()) {
            val productList = productDao!!.searchProductByName(message)
            if (productList.isNotEmpty()) {
                val mRecyclerView: RecyclerView = findViewById(R.id.search_recyclerview)
                val mAdapter = ProductAdapter(this, productList)
                mRecyclerView.adapter = mAdapter
                mRecyclerView.layoutManager = LinearLayoutManager(this)
            } else {
                Toast.makeText(applicationContext, "没有搜索到${message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(applicationContext, "请先填写商品名称", Toast.LENGTH_SHORT).show()
        }
    }
}