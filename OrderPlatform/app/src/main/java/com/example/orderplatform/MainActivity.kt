package com.example.orderplatform

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.orderplatform.adapter.PagerAdapter
import com.example.orderplatform.database.MDataBaseHelper
import com.example.orderplatform.database.OrderDao
import com.example.orderplatform.database.ProductDao
import com.example.orderplatform.entity.Product
import com.example.orderplatform.utils.ScaleInTransformer
import com.example.orderplatform.view.Search
import com.example.orderplatform.view.ShopCart
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {
    private var drawer: DrawerLayout? = null

    private var mViewPager: ViewPager2? = null

    private var mHelper: MDataBaseHelper? = null

    private var mNotifyManager: NotificationManager? = null

    // 初始化 dao 层，进行 sql 操作
    private var productDao: ProductDao? = null

    private var orderDao: OrderDao? = null

    companion object {
        private const val NOTIFICATION_ID = 0

        private const val PRIMARY_CHANNEL_ID = "primary_notification_channel"

        private const val ACTION_UPDATE_NOTIFICATION =
            "com.example.orderplatform.ACTION_UPDATE_NOTIFICATION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawer = findViewById(R.id.drawer_layout)

        // 创建数据库连接
        mHelper = MDataBaseHelper(this)
        productDao = ProductDao(mHelper!!)
        orderDao = OrderDao(mHelper!!)

        val mNavigationView: NavigationView = findViewById(R.id.nav_view)
        mNavigationView.setNavigationItemSelectedListener(this)

        // itemsCount 指定 Fragment 数量
        // ViewPager2 会自动加载一个 Fragment 两边的 Fragment 保证流畅
        val pagerAdapter = PagerAdapter(
            supportFragmentManager,
            lifecycle,
            6,
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
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        // 设置 navigation 必须在设置 SupportActionBar 之后
        toolbar.setNavigationOnClickListener {
            drawer?.openDrawer(GravityCompat.START)
        }

        initData()
        createNotificationChannel()

        if (orderDao!!.findNotPay(0).isNotEmpty()) {
            sendNotification()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_app_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // 设置 AppBar 中的切换
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_setting -> {
                drawer?.openDrawer(GravityCompat.START)
            }

            R.id.action_search -> {
                val intent = Intent(this, Search::class.java)
                startActivity(intent)
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

            R.id.history_order -> {
                mViewPager?.currentItem = 5
                drawer?.closeDrawer(GravityCompat.START)
                return true
            }
        }
        return false
    }

    private fun sendNotification() {
        val intent = Intent(ACTION_UPDATE_NOTIFICATION)
        val updatePendingIntent =
            PendingIntent.getBroadcast(
                this,
                NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
            )
        val notifyBuilder = getNotificationBuilder()
        mNotifyManager!!.notify(NOTIFICATION_ID, notifyBuilder.build())
    }

    private fun getNotificationBuilder(): NotificationCompat.Builder {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        return NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
            .setContentTitle("你有未支付的订单")
            .setContentText("请尽快支付你的订单")
            .setSmallIcon(R.drawable.ic_android)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
    }

    private fun createNotificationChannel() {
        mNotifyManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager?
        val message = "未支付提醒"
        val notificationChannel = NotificationChannel(
            PRIMARY_CHANNEL_ID,
            message,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationChannel.enableLights(true)
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
        notificationChannel.description = message
        mNotifyManager!!.createNotificationChannel(notificationChannel)
    }

    private fun initData() {
        if (productDao!!.findAll().isEmpty()) {
            productDao!!.insert(
                product = Product(
                    "鸡爪",
                    0,
                    10,
                    R.string.dinner1_word,
                    R.drawable.dinner_pic1,
                    0
                )
            )
            productDao!!.insert(
                product = Product(
                    "烤猪排",
                    0,
                    20,
                    R.string.dinner2_word,
                    R.drawable.dinner_pic2,
                    0
                )
            )
            productDao!!.insert(
                product = Product(
                    "小龙虾",
                    0,
                    30,
                    R.string.dinner3_word,
                    R.drawable.dinner_pic3,
                    0
                )
            )
            productDao!!.insert(
                product = Product(
                    "冰红茶",
                    1,
                    5,
                    R.string.drink1_word,
                    R.drawable.drink_pic1,
                    0
                )
            )
            productDao!!.insert(
                product = Product(
                    "可口可乐",
                    1,
                    5,
                    R.string.drink2_word,
                    R.drawable.drink_pic2,
                    0
                )
            )
            productDao!!.insert(
                product = Product(
                    "汉堡",
                    2,
                    7,
                    R.string.fastfood1_word,
                    R.drawable.fastfood_pic1,
                    0
                )
            )
            productDao!!.insert(
                product = Product(
                    "提拉米苏",
                    3,
                    15,
                    R.string.desert1_word,
                    R.drawable.dessert_pic1,
                    0
                )
            )
        }
    }
}