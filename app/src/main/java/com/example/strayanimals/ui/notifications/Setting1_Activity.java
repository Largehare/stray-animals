package com.example.strayanimals.ui.notifications;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.strayanimals.R;

public class Setting1_Activity extends AppCompatActivity {
    private ListView sListView;
    private  String[] names={"修改头像","修改昵称","修改个人介绍"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting1);
        sListView=(ListView)findViewById(R.id.setting_lv);
        sListView.setAdapter(new MyBaseAdapter());
    }
    class MyBaseAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return names.length;
        }
        @Override
        public Object getItem(int position) {
            return names [position];
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {//组装数据
            View view=View.inflate(Setting1_Activity.this,R.layout.list_item,null);//在list_item中有两个id,现在要把他们拿过来
            TextView mTextView=(TextView) view.findViewById(R.id.tv_list);
            ImageView imageView=(ImageView)view.findViewById(R.id.image);
            //组件一拿到，开始组装
            mTextView.setText(names[position]);
            return view;
        }
    }
}