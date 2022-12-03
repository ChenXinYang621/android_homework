package com.example.orderplatform.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import com.example.orderplatform.MainActivity
import com.example.orderplatform.R
import com.example.orderplatform.database.MDataBaseHelper
import com.example.orderplatform.database.OrderDao
import com.example.orderplatform.database.ProductDao
import com.example.orderplatform.entity.Order
import com.example.orderplatform.utils.TimePicker

class OrderActivity : AppCompatActivity(), OnClickListener {
    private var mHelper: MDataBaseHelper? = null

    private var productDao: ProductDao? = null

    private var orderDao: OrderDao? = null

    private var nameEditText: EditText? = null

    private var addressEditText: EditText? = null

    private var otherEditText: EditText? = null

    private var radioGroup: RadioGroup? = null

    private var needTool: SwitchCompat? = null

    private var needTouch: SwitchCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        mHelper = MDataBaseHelper(this)
        productDao = ProductDao(mHelper!!)
        orderDao = OrderDao(mHelper!!)

        nameEditText = findViewById(R.id.edit_name)
        addressEditText = findViewById(R.id.edit_address)
        otherEditText = findViewById(R.id.edit_other)
        radioGroup = findViewById(R.id.order_radio_group)
        needTool = findViewById(R.id.tool)
        needTouch = findViewById(R.id.touch)

        findViewById<Button>(R.id.order_submit).setOnClickListener(this)

        val toolbar: Toolbar = findViewById(R.id.order_toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.order_submit -> {
                val timePicker = TimePicker()
                timePicker.show(supportFragmentManager, "timePicker")
            }
        }
    }

    fun processTimePickerResult(hour: Int, minute: Int) {
        val message = "运送时间设定为${hour}:${minute}"
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        val productList = productDao!!.findProductOverNum(0)
        for (product in productList) {
            productDao!!.updateNum(product.name, 0)
        }

        val order = Order(
            nameEditText!!.text.toString(),
            addressEditText!!.text.toString(),
            otherEditText!!.text.toString(),
            message,
            radioGroup!!.checkedRadioButtonId,
            needTool!!.isChecked.toInt(),
            needTouch!!.isChecked.toInt(),
            0
        )
        val builder = AlertDialog.Builder(this)
        builder.setTitle("是否现在进行付款")
        builder.setPositiveButton("付款") { _, _ ->
            Toast.makeText(applicationContext, "付款完成，请享用", Toast.LENGTH_SHORT).show()
            order.pay = 1
            orderDao!!.insert(order)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        builder.setNegativeButton("不付款") { _, _ ->
            orderDao!!.insert(order)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        builder.create().show()
    }

    // 使用扩展函数进行转化
    private fun Boolean.toInt() = if (this) 1 else 0
}