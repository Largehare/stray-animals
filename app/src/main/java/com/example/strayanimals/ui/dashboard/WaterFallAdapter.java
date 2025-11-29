package com.example.strayanimals.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.strayanimals.R;
import com.example.strayanimals.data.Post;

import java.util.List;

public class WaterFallAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<Post> mData;
    private OnItemClickListener mOnItemClickListener;

    public WaterFallAdapter(Context context, List<Post> data) {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public WaterFallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.waterfall_item, null);
        return new WaterFallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        WaterFallViewHolder _holder = (WaterFallViewHolder) holder;
        Post tweet = mData.get(position);
        //_holder.wfTitle.setText(tweet.getTitle());
        _holder.wfDigest.setText(tweet.getContent());

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(view -> mOnItemClickListener.onItemClick(view, position));
        }
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public class WaterFallViewHolder extends RecyclerView.ViewHolder {
        // public ImageView wfImageView;
        public TextView wfTitle;
        public TextView wfDigest;

        public WaterFallViewHolder(View itemView) {
            super(itemView);
            //wfTitle = (TextView) itemView.findViewById(R.id.wftextView_title);
            wfDigest = (TextView) itemView.findViewById(R.id.wftextView_digest);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
