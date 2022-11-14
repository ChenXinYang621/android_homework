package com.example.recipelist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MenuViewHolder> {
    private final LinkedList<String> mTitleList;

    private final LinkedList<String> mContentList;

    private final LinkedList<String> mRecipeList;

    private final LinkedList<Integer> mPhotoList;

    private final LayoutInflater mInflater;

    private final Context context;

    public MenuListAdapter(Context context,
                           LinkedList<String> mTitleList,
                           LinkedList<String> mContentList,
                           LinkedList<String> mRecipeList,
                           LinkedList<Integer> mPhotoList) {
        this.context = context;
        this.mTitleList = mTitleList;
        this.mContentList = mContentList;
        this.mRecipeList = mRecipeList;
        this.mPhotoList = mPhotoList;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.menulist_item, parent, false);
        return new MenuViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        holder.mTitleView.setText(mTitleList.get(position));
        holder.mContentView.setText(mContentList.get(position));
    }

    @Override
    public int getItemCount() {
        return mTitleList.size();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mTitleView;

        public final TextView mContentView;

        final MenuListAdapter mAdapter;

        public MenuViewHolder(@NonNull View itemView, MenuListAdapter adapter) {
            super(itemView);
            this.mAdapter = adapter;
            mTitleView = itemView.findViewById(R.id.menu);
            mContentView = itemView.findViewById(R.id.content);
            mTitleView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();
            Intent intent = new Intent(context, SecondActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("title", mTitleList.get(mPosition));
            bundle.putString("recipe", mRecipeList.get(mPosition));
            bundle.putInt("photo", mPhotoList.get(mPosition));
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }
}
