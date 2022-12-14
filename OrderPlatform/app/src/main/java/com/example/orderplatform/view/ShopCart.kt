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
                    Toast.makeText(applicationContext, "您还没选购任何商品", Toast.LENGTH_SHORT)
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
                builder.setTitle("你确定要全部删除吗")
                builder.setPositiveButton("确定") { _, _ ->
                    for (product in productList!!) {
                        productDao!!.updateNum(product.name, 0)
                    }
                    clearData()
                    initData()
                    Toast.makeText(applicationContext, "全部删除已经完成", Toast.LENGTH_SHORT)
                        .show()
                }
                builder.setNegativeButton("取消") { _, _ ->
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
                Toast.makeText(applicationContext, "${str}删除已经完成", Toast.LENGTH_SHORT).show()
                clearData()
                initData()
            }

            R.id.shop_cart_edit -> {
                val str = productList!![mAdapter!!.mPosition].name
                val builder = AlertDialog.Builder(this)
                val numbers = arrayOf("1份", "2份", "3份", "4份", "5份")
                builder.setTitle("请选择你需要的数量")
                builder.setSingleChoiceItems(numbers, 0) { _, i ->
                    selectId = i
                    Toast.makeText(
                        applicationContext,
                        "你选择了${numbers[i]}的${str}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                builder.setPositiveButton("确定") { _, _ ->
                    productDao!!.updateNum(str, selectId + 1)
                    Toast.makeText(applicationContext, "修改完成", Toast.LENGTH_SHORT).show()
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