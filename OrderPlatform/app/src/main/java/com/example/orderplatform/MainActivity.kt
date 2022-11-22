package com.example.orderplatform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.orderplatform.adapter.PagerAdapter
import com.example.orderplatform.database.MDataBaseHelper
import com.example.orderplatform.database.ProductDao
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var drawer: DrawerLayout? = null

    private var mViewPager: ViewPager2? = null

    private var mHelper: MDataBaseHelper? = null
//
    private var productDao: ProductDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawer = findViewById(R.id.drawer_layout)
        val mNavigationView: NavigationView = findViewById(R.id.nav_view)
        mNavigationView.setNavigationItemSelectedListener(this)

        val pagerAdapter = PagerAdapter(supportFragmentManager, lifecycle, 6)
        mViewPager = findViewById(R.id.pager)
        mViewPager?.adapter = pagerAdapter

        mNavigationView.setCheckedItem(R.id.dinner)
        setSupportActionBar(findViewById(R.id.toolbar))

        productDao = mHelper?.let { ProductDao.getInstance() }
//        initData()
    }

    override fun onStart() {
        super.onStart()
        mHelper = MDataBaseHelper.getInstance(this)
        mHelper!!.openWriteLink()
        mHelper!!.closeLink()
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

//        productDao!!.insert(product = Product(""))
    }
}