package com.example.orderplatform.adapter

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import java.util.LinkedList

class DinnerAdapter(
    context: Context,
    mTitleList: LinkedList<String>,
    mDescriptionList: LinkedList<String>,
    mPhotoList: LinkedList<String>,
    mContentList: LinkedList<String>
) : RecyclerView.Adapter<DinnerAdapter.DinnerViewHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    class DinnerViewHolder(itemView: View, adapter: DinnerAdapter) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DinnerViewHolder {
//        mItemView = mInflater.inflate(R.layout.)
        TODO("Not yet")
    }

    override fun onBindViewHolder(holder: DinnerViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}