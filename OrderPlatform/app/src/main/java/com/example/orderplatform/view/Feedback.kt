package com.example.orderplatform.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.orderplatform.MainActivity
import com.example.orderplatform.R
import com.example.orderplatform.database.MDataBaseHelper
import com.example.orderplatform.database.OrderDao
import com.example.orderplatform.entity.Order

class Feedback : AppCompatActivity(), OnClickListener {

    private var mHelper: MDataBaseHelper? = null

    private var orderDao: OrderDao? = null

    private var mComment: EditText? = null

    private var mRatingBar: RatingBar? = null

    private var order: Order? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        mHelper = MDataBaseHelper(this)
        orderDao = OrderDao(mHelper!!)

        val bundle = intent.extras
        val id = bundle!!.getInt("id")
        order = orderDao!!.findOrderById(id)

        mComment = findViewById(R.id.feedback_word)
        mRatingBar = findViewById(R.id.feedback_ratingBar)
        val button: Button = findViewById(R.id.feedback_submit)
        button.setOnClickListener(this)

        when (order!!.pay) {
            0 -> {
                orderDao!!.updatePayById(id, 1)
                Toast.makeText(applicationContext, "${order!!.name}支付成功", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        val message = order!!.feedback
        val star = order!!.star
        message?.let { it1 ->
            star?.let { it2 ->
                // 设置 Button 为不可编辑且更改内容
                button.isClickable = false
                button.text = "已提交"
                button.setTextColor(getColor(R.color.black))
                button.setBackgroundColor(getColor(R.color.grey))
                mComment!!.setText(it1.toCharArray(), 0, it1.length)
                // 设置为不可编辑
                mComment!!.isFocusable = false
                mComment!!.isFocusableInTouchMode = false
                mRatingBar!!.rating = it2.toFloat()
                mRatingBar!!.setIsIndicator(true)
            }
        }

        val toolbar: Toolbar = findViewById(R.id.feedback_toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.feedback_submit -> {
                val id = order!!.id
                val message = mComment!!.text.toString()
                val star = mRatingBar!!.rating.toDouble()
                orderDao!!.updateFeedBackById(id, message, star)
                Toast.makeText(applicationContext, "评价成功", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}