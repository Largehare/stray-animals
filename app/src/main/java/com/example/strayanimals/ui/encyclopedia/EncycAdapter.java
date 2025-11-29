package com.example.strayanimals.ui.encyclopedia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.strayanimals.R;
import com.example.strayanimals.ui.encyclopedia.encinterface.OnItemClickListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class EncycAdapter extends RecyclerView.Adapter<EncycAdapter.EncycViewHolder> {
    private List<EncycBean> data;
    private Context context;
    protected OnItemClickListener mOnItemClickListener;
    public EncycAdapter(List<EncycBean> data, Context context) {
        this.data = data;
        this.context = context;
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    @NonNull
    @Override
    public EncycViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //第二个需要传入一个parent,因为parent如果为null,那么根布局不会去绘制,到时候显示出来就可能会填充不满,而如果不为null,adapter就会根据你给item设置的大小去显示;
        View view = LayoutInflater.from(context).inflate(R.layout.encyc_item,parent,false);
        return new EncycViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EncycViewHolder holder, int position) {
        EncycBean thisBean = data.get(position);
        holder.bind(thisBean);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(view,getItemCount(),data.get(holder.getPosition()).getUrl());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
            return data==null?0:data.size();
    }

    public class EncycViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout tv;
        private RoundedImageView mImageView;
        private TextView mTitle;
        private TextView mContent;
        private String mUrl;
        public EncycViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tips_item);
            mImageView = itemView.findViewById(R.id.iv_goods_img);
            mTitle = itemView.findViewById(R.id.iv_goods_name);
            mContent = itemView.findViewById(R.id.iv_goods_comment);

        }
        public void bind(EncycBean encycBean) {
            mTitle.setText(encycBean.getTitle());
            mContent.setText(encycBean.getContent());
//            mImageView.setText(encycBean.getDate());
            Glide.with(context).load(encycBean.getImg()).error(R.drawable.brazuca_dog).into(mImageView);
        }
    }
}
