package com.example.orderplatform.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.orderplatform.view.MoreContent
import com.example.orderplatform.R

class ProductAdapter(
    val context: Context,
    private val mTitle: List<String>,
    private val mPrice: List<Int>,
    private val mDescription: List<Int>,
    private val mPicture: List<Int>
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    // 申明为 inner 的内部类可以访问外部变量
    inner class ProductViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val mTitleView: TextView = itemView.findViewById(R.id.product_title)
        val mPriceView: TextView = itemView.findViewById(R.id.product_price)
        val mImageView: ImageView = itemView.findViewById(R.id.product_image)

        init {
            mTitleView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = layoutPosition
            val intent = Intent(context, MoreContent::class.java)
            val bundle = Bundle()
            bundle.putString("title", mTitle[position])
            bundle.putInt("description", mDescription[position])
            bundle.putInt("picture", mPicture[position])
            intent.putExtras(bundle)
            context.startActivity(intent)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val mItemView = mInflater.inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(mItemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.mTitleView.text = mTitle[position]
        holder.mPriceView.text = mPrice[position].toString()
        holder.mImageView.setImageResource(mPicture[position])
    }

    override fun getItemCount(): Int {
        return mTitle.size
    }
}