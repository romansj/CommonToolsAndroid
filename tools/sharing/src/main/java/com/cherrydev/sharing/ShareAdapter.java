package com.cherrydev.sharing;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cherrydev.commontools.R;

import java.util.List;

public class ShareAdapter extends RecyclerView.Adapter<ShareAdapter.ViewHolder> {

    public void setmData(List<DialogItemShare> mData) {
        if (this.mData != null) {
            this.mData.clear();
        }
        this.mData = mData;
        notifyDataSetChanged();
    }

    private List<DialogItemShare> mData;
    private ItemClickListener mClickListener;


    private Intent mIntent;

    // data is passed into the constructor
    public ShareAdapter(List<DialogItemShare> data, Intent intent) {
        this.mData = data;
        this.mIntent = intent;
    }

    public void updateData(List<DialogItemShare> newData, Intent intent) {
        this.mData = newData;
        this.mIntent = intent;
        notifyDataSetChanged();
    }

    public void setmIntent(Intent mIntent) {
        this.mIntent = mIntent;
    }

    @NonNull
    @Override
    public ShareAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_share_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShareAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageDrawable(mData.get(holder.getAdapterPosition()).icon);
        holder.myTextView.setText(mData.get(holder.getAdapterPosition()).app);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    // convenience method for getting data at click position
    DialogItemShare getItem(int position) {
        return mData.get(position);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public Intent getIntent() {
        return mIntent;
    }


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onShareRVItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView myTextView;


        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            myTextView = itemView.findViewById(R.id.tvShare);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onShareRVItemClick(view, getAdapterPosition());
            }
        }
    }
}