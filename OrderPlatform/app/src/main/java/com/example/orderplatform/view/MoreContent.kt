package com.example.orderplatform.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.orderplatform.R
import com.example.orderplatform.database.MDataBaseHelper
import com.example.orderplatform.database.ProductDao
import com.example.orderplatform.entity.Product
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MoreContent : AppCompatActivity(), OnClickListener {
    private var mHelper: MDataBaseHelper? = null

    private var productDao: ProductDao? = null

    private var mTitle: String? = null

    private var product: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_content)

        val bundle = intent.extras
        val title: TextView = findViewById(R.id.more_title)
        val description: TextView = findViewById(R.id.more_description)
        val picture: ImageView = findViewById(R.id.more_picture)

        mTitle = bundle!!.getString("title")
        title.text = mTitle
        description.text = getString(bundle.getInt("description"))
        picture.setImageResource(bundle.getInt("picture"))

        mHelper = MDataBaseHelper(this)
        productDao = ProductDao(mHelper!!)

        product = mTitle?.let { productDao!!.findProductByName(it) }
        findViewById<FloatingActionButton>(R.id.more_fab).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.more_fab -> {
                product!!.num++
                productDao!!.updateNum(product!!.name, product!!.num)
                Toast.makeText(
                    applicationContext,
                    "已经添加${product!!.num}个${product!!.name}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}