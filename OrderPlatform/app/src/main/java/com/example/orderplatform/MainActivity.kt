package com.example.orderplatform

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.orderplatform.adapter.PagerAdapter
import com.example.orderplatform.database.MDataBaseHelper
import com.example.orderplatform.database.ProductDao
import com.example.orderplatform.entity.Product
import com.example.orderplatform.utils.ScaleInTransformer
import com.example.orderplatform.view.ShopCart
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
        mHelper = MDataBaseHelper(this)
        productDao = ProductDao(mHelper!!)

        val mNavigationView: NavigationView = findViewById(R.id.nav_view)
        mNavigationView.setNavigationItemSelectedListener(this)

        // itemsCount 指定 Fragment 数量
        // ViewPager2 会自动加载一个 Fragment 两边的 Fragment 保证流畅
        val pagerAdapter = PagerAdapter(
            supportFragmentManager,
            lifecycle,
            5,
            this
        )
        // ViewPager 在创建时调用 createFragment 创建所有对应 Fragment，默认支持滑动切换
        mViewPager = findViewById(R.id.pager)
        // 取消滑动切换
        mViewPager!!.isUserInputEnabled = false

        // 添加自定义动画效果
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(ScaleInTransformer())
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        mViewPager!!.setPageTransformer(compositePageTransformer)
        mViewPager!!.adapter = pagerAdapter

        mNavigationView.setCheckedItem(R.id.dinner)
        setSupportActionBar(findViewById(R.id.toolbar))
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
            R.id.action_shop -> {
                val intent = Intent(this, ShopCart::class.java)
                startActivity(intent)
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
            R.id.fast_food -> {
                mViewPager?.currentItem = 2
                drawer?.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.desert -> {
                mViewPager?.currentItem = 3
                drawer?.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.data -> {
                mViewPager?.currentItem = 4
                drawer?.closeDrawer(GravityCompat.START)
                return true
            }
        }
        return false
    }

    private fun initData() {
        if (productDao!!.findAll().isEmpty()) {
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
                    "烤猪排",
                    0,
                    20,
                    R.string.烤猪排,
                    R.drawable.dinner_pic2,
                    0
                )
            )
            productDao!!.insert(
                product = Product(
                    3,
                    "冰红茶",
                    1,
                    5,
                    R.string.冰红茶,
                    R.drawable.drink_pic1,
                    0
                )
            )
            productDao!!.insert(
                product = Product(
                    4,
                    "汉堡",
                    2,
                    7,
                    R.string.汉堡,
                    R.drawable.fastfood_pic1,
                    0
                )
            )
            productDao!!.insert(
                product = Product(
                    5,
                    "提拉米苏",
                    3,
                    15,
                    R.string.提拉米苏,
                    R.drawable.dessert_pic1,
                    0
                )
            )
        }
        val productList = productDao!!.findProductByCatalogue(1)
        for (product in productList) {
            Log.d("test_show", product.name)
        }
    }
}