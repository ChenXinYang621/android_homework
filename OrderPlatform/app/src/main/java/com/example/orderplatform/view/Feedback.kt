package com.example.orderplatform.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.orderplatform.R
import com.example.orderplatform.database.MDataBaseHelper
import com.example.orderplatform.database.OrderDao

class Feedback : AppCompatActivity() {

    private var mHelper: MDataBaseHelper? = null

    private var orderDao: OrderDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        mHelper = MDataBaseHelper(this)
        orderDao = OrderDao(mHelper!!)

        val bundle = intent.extras
        val id = bundle!!.getInt("id")
        var order = orderDao!!.findOrderById(id)

        if (order!!.pay == 0) {
            orderDao!!.updatePay(id, 1)
            order = orderDao!!.findOrderById(id)
        }

//        findViewById<TextView>(R.id.test).text = order!!.name
    }
}