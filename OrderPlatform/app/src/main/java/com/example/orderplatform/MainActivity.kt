package com.example.orderplatform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.orderplatform.adapter.PagerAdapter
import com.example.orderplatform.database.MDataBaseHelper
import com.example.orderplatform.database.ProductDao
import com.example.orderplatform.entity.Product
import com.google.android.material.navigation.NavigationView
import kotlin.math.log

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var drawer: DrawerLayout? = null

    private var mViewPager: ViewPager2? = null

    private var mHelper: MDataBaseHelper? = null

    // 初始化 dao 层，进行 sql 操作
    private var productDao: ProductDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawer = findViewById(R.id.drawer_layout)

        // 创建数据库连接
        mHelper = MDataBaseHelper.getInstance(this)

        val mNavigationView: NavigationView = findViewById(R.id.nav_view)
        mNavigationView.setNavigationItemSelectedListener(this)

        val pagerAdapter = PagerAdapter(
            supportFragmentManager,
            lifecycle,
            6,
            this
        )
        mViewPager = findViewById(R.id.pager)
        mViewPager!!.adapter = pagerAdapter

        mNavigationView.setCheckedItem(R.id.dinner)
        setSupportActionBar(findViewById(R.id.toolbar))

        productDao = mHelper?.let { ProductDao.getInstance() }
    }

    override fun onStart() {
        super.onStart()
        mHelper!!.openWriteLink()
        mHelper!!.openReadLink()
        initData()
    }

    override fun onStop() {
        super.onStop()
        mHelper!!.closeLink()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // 设置 AppBar 中的切换
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_setting -> {
                drawer?.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // 配置 NavigationView 进行多 Fragment 切换
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.dinner -> {
                mViewPager?.currentItem = 0
                drawer?.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.drink -> {
                mViewPager?.currentItem = 1
                drawer?.closeDrawer(GravityCompat.START)
                return true
            }
        }
        return false
    }

    private fun initData() {
        val value = productDao!!.deleteAll()
        Log.d("deleteName", "delete $value data")
        productDao!!.insert(
            product = Product(
                1,
                "鸡爪",
                0,
                10,
                R.string.鸡爪,
                R.drawable.dinner_pic1,
                0
            )
        )
        productDao!!.insert(
            product = Product(
                2,
                "冰红茶",
                1,
                5,
                R.string.冰红茶,
                R.drawable.drink_pic1,
                0
            )
        )
        val productList = productDao!!.findProductByCatalogue(1)
        for (product in productList) {
            Log.d("test_show", product.name.toString())
        }
    }
}