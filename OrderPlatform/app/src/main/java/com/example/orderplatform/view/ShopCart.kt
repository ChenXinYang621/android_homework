package com.example.orderplatform.view

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.orderplatform.R
import com.example.orderplatform.adapter.ShopCartAdapter
import com.example.orderplatform.database.MDataBaseHelper
import com.example.orderplatform.database.ProductDao
import com.example.orderplatform.entity.Product
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ShopCart : AppCompatActivity(), OnClickListener {

    private var mHelper: MDataBaseHelper? = null

    private var productDao: ProductDao? = null

    private var mAdapter: ShopCartAdapter? = null

    private var productList: List<Product>? = null

    private var register: ActivityResultLauncher<Intent>? = null

    private var selectId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_cart)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        register = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            initData()
        }
        initData()
        findViewById<FloatingActionButton>(R.id.shop_cart_fab).setOnClickListener(this)
        savedInstanceState?.let {
            initData()
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.shop_cart_fab -> {
                val count = productDao!!.findProductOverNum(0).size
                if (count > 0) {
                    val intent = Intent(this, OrderActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, "???????????????????????????", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shop_cart_app_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_clear -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("???????????????????????????")
                builder.setPositiveButton("??????") { _, _ ->
                    for (product in productList!!) {
                        productDao!!.updateNum(product.name, 0)
                    }
                    clearData()
                    initData()
                    Toast.makeText(applicationContext, "????????????????????????", Toast.LENGTH_SHORT)
                        .show()
                }
                builder.setNegativeButton("??????") { _, _ ->
                }
                builder.create().show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.shop_cart, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shop_cart_delete -> {
                val str = productList!![mAdapter!!.mPosition].name
                productDao!!.updateNum(str, 0)
                Toast.makeText(applicationContext, "${str}??????????????????", Toast.LENGTH_SHORT).show()
                clearData()
                initData()
            }

            R.id.shop_cart_edit -> {
                val str = productList!![mAdapter!!.mPosition].name
                val builder = AlertDialog.Builder(this)
                val numbers = arrayOf("1???", "2???", "3???", "4???", "5???")
                builder.setTitle("???????????????????????????")
                builder.setSingleChoiceItems(numbers, 0) { _, i ->
                    selectId = i
                    Toast.makeText(
                        applicationContext,
                        "????????????${numbers[i]}???${str}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                builder.setPositiveButton("??????") { _, _ ->
                    productDao!!.updateNum(str, selectId + 1)
                    Toast.makeText(applicationContext, "????????????", Toast.LENGTH_SHORT).show()
                    clearData()
                    initData()
                }
                builder.create().show()
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun initData() {
        mHelper = MDataBaseHelper(this)
        productDao = ProductDao(mHelper!!)
        productList = productDao!!.findProductOverNum(0)
        val mRecyclerView: RecyclerView = findViewById(R.id.shop_cart_view)
        registerForContextMenu(mRecyclerView)
        mAdapter = ShopCartAdapter(this, productList!!, register!!)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun clearData() {
        productList = mutableListOf()
    }
}