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
import androidx.recyclerview.widget.RecyclerView
import com.example.orderplatform.R
import com.example.orderplatform.entity.Order
import com.example.orderplatform.view.Feedback

class OrderAdapter(
    private val context: Context,
//    private val mId: List<Int>,
//    private val mTitle: List<String>,
//    private val mState: List<Int>
    private val orderList: List<Order>
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
                R.id.order_item_title, R.id.order_item_button -> {
                    val position = layoutPosition
                    val intent = Intent(context, Feedback::class.java)
                    val bundle = Bundle()
                    orderList[position].id?.let { bundle.putInt("id", it) }
                    intent.putExtras(bundle)
                    context.startActivity(intent)
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
        when (order.pay) {
            1 -> {
                holder.mButtonView.isClickable = false
                holder.mButtonView.text = "已付款"
                holder.mButtonView.setBackgroundColor(context.getColor(R.color.grey))
            }
            0 -> {
                holder.mButtonView.text = "未付款"
                holder.mButtonView.setBackgroundColor(context.getColor(R.color.purple_500))
            }
        }
    }

    override fun getItemCount(): Int {
        return orderList.size
    }
}