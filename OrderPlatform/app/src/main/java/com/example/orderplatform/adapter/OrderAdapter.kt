package com.example.orderplatform.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.example.orderplatform.R
import com.example.orderplatform.entity.Order
import com.example.orderplatform.view.Feedback

class OrderAdapter(
    private val context: Context,
    private val orderList: List<Order>,
    private val register: ActivityResultLauncher<Intent>
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    inner class OrderViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), OnClickListener {

        val mTitleView: TextView = itemView.findViewById(R.id.order_item_title)
        val mButtonView: Button = itemView.findViewById(R.id.order_item_button)

        init {
            mTitleView.setOnClickListener(this)
            mButtonView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            when (v!!.id) {
                R.id.order_item_title -> {
                    val position = layoutPosition
                    val intent = Intent(context, Feedback::class.java)
                    val bundle = Bundle()
                    orderList[position].id.let { bundle.putInt("id", it) }
                    intent.putExtras(bundle)
                    context.startActivity(intent)
                }
                R.id.order_item_button -> {
                    val position = layoutPosition
                    val intent = Intent(context, Feedback::class.java)
                    val bundle = Bundle()
                    orderList[position].id.let { bundle.putInt("id", it) }
                    intent.putExtras(bundle)
                    register.launch(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val mItemView = mInflater.inflate(R.layout.order_item, parent, false)
        return OrderViewHolder(mItemView)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        holder.mTitleView.text = order.name
        val button = holder.mButtonView
        if (order.pay == 1) {
            Log.d("支付成功", "支付成功")
            button.isClickable = false
            button.text = "已付款"
            button.setBackgroundColor(context.getColor(R.color.grey))
            button.setTextColor(context.getColor(R.color.black))
        }
    }

    override fun getItemCount(): Int {
        return orderList.size
    }
}