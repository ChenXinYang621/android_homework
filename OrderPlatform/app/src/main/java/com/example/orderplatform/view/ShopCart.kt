package com.example.orderplatform.view

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.orderplatform.R
import com.example.orderplatform.adapter.ProductAdapter
import com.example.orderplatform.adapter.ShopCartAdapter
import com.example.orderplatform.database.MDataBaseHelper
import com.example.orderplatform.database.ProductDao

class ShopCart : AppCompatActivity() {
    private var mHelper: MDataBaseHelper? = null

    private var productDao: ProductDao? = null

    private var mAdapter: ShopCartAdapter? = null

    private val mTitle = mutableListOf<String>()

    private val mDescription = mutableListOf<Int>()

    private val mPicture = mutableListOf<Int>()

    private val mNum = mutableListOf<Int>()

    private var selectId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_cart)
        initData()
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
                val str = mTitle[mAdapter!!.mPosition]
                productDao!!.updateNum(str, 0)
                Toast.makeText(applicationContext, "${str}删除已经完成，请刷新", Toast.LENGTH_SHORT).show()
                clearData()
                initData()
            }
            R.id.shop_cart_edit -> {
                val str = mTitle[mAdapter!!.mPosition]
                val builder = AlertDialog.Builder(this)
                val numbers = arrayOf("1份", "2份", "3份", "4份", "5份")
                builder.setTitle("请选择你需要的数量")
                builder.setSingleChoiceItems(numbers, 0) { _, i ->
                    selectId = i
                    Toast.makeText(applicationContext, "你选择了${numbers[i]}的${str}", Toast.LENGTH_SHORT).show()
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
//        return super.onContextItemSelected(item)
        return true
    }

    private fun initData() {
        mHelper = MDataBaseHelper(this)
        productDao = ProductDao(mHelper!!)
        val productList = productDao!!.findProductOverNum(0)
        for (product in productList) {
            mTitle.add(0, product.name)
            mDescription.add(0, product.word)
            mPicture.add(0, product.picture)
            mNum.add(0, product.num)
        }

        val mRecyclerView: RecyclerView = findViewById(R.id.shop_cart_view)
        registerForContextMenu(mRecyclerView)
        mAdapter = ShopCartAdapter(this, mTitle, mDescription, mPicture, mNum)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun clearData() {
        mTitle.clear()
        mDescription.clear()
        mPicture.clear()
        mNum.clear()
    }
}