package com.example.orderplatform.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.orderplatform.R

class OrderAdapter(
    private val context: Context,
    private val mId: List<Int>,
    private val mTitle: List<String>,
    private val mState: List<Int>
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    inner class OrderViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), OnClickListener {

        val mTitleView: TextView = itemView.findViewById(R.id.order_item_title)
        val mButtonView: Button = itemView.findViewById(R.id.order_item_button)

        init {
            mTitleView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Log.d("test_new", "yes")
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val mItemView = mInflater.inflate(R.layout.order_item, parent, false)
        return OrderViewHolder(mItemView)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.mTitleView.text = mTitle[position]
    }

    override fun getItemCount(): Int {
        return mTitle.size
    }
}