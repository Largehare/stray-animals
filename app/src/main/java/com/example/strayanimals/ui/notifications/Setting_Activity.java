package com.example.strayanimals.ui.notifications;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.strayanimals.R;
import com.example.strayanimals.ui.login.LoginActivity;

public class Setting_Activity extends AppCompatActivity {
    private ListView sListView;
    private String[] names = {"个人资料设置", "偏好设置"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sListView = (ListView) findViewById(R.id.setting_lv);
        sListView.setAdapter(new MyBaseAdapter());
        Button bt1 = findViewById(R.id.quit);
        bt1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setting_Activity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    class MyBaseAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            return names[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {//组装数据
            View view = View.inflate(Setting_Activity.this, R.layout.list_item, null);//在list_item中有两个id,现在要把他们拿过来
            TextView mTextView = (TextView) view.findViewById(R.id.tv_list);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            //组件一拿到，开始组装
            mTextView.setText(names[position]);
            return view;
        }
    }
}