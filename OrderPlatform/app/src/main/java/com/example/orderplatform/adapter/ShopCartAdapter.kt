package com.example.orderplatform.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.example.orderplatform.R
import com.example.orderplatform.entity.Product
import com.example.orderplatform.view.MoreContent

class ShopCartAdapter(
    private val context: Context,
    private val productList: List<Product>,
    private val register: ActivityResultLauncher<Intent>
) : RecyclerView.Adapter<ShopCartAdapter.ShopCartViewHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    var mPosition = -1

    inner class ShopCartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        OnClickListener, OnLongClickListener {

        val mTitleView: TextView = itemView.findViewById(R.id.shop_cart_item)

        init {
            mTitleView.setOnClickListener(this)
            mTitleView.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = layoutPosition
            val intent = Intent(context, MoreContent::class.java)
            val bundle = Bundle()
            bundle.putString("title", productList[position].name)
            bundle.putInt("description", productList[position].word)
            bundle.putInt("picture", productList[position].picture)
            bundle.putInt("flag", 1)
            intent.putExtras(bundle)
            register.launch(intent)
        }

        override fun onLongClick(v: View?): Boolean {
            mPosition = adapterPosition
            return false
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ShopCartViewHolder {
        val mItemView = mInflater.inflate(R.layout.shop_cart_item, parent, false)
        return ShopCartViewHolder(mItemView)
    }

    // 使用规范的 placeholder 格式设置 TextView
    override fun onBindViewHolder(holder: ShopCartViewHolder, position: Int) {
        holder.mTitleView.text =
            context.getString(
                R.string.shop_cart_item,
                productList[position].name,
                productList[position].num
            )
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}