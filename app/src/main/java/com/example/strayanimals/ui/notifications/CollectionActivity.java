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

public class CollectionActivity extends AppCompatActivity {
    private ListView sListView;
    private  String[] names={"北京市昌平区一流浪狗","朝阳区一只小可爱待收养","坐标河南郑州，最近小区附近有流浪狗","人在南京，经常遇到一直可爱小狗","海淀区一直流浪狗求收养","坐标山西太原，门口经常有只流浪狗"};
    private int[] icons={R.drawable.dog_1,R.drawable.dog_7,R.drawable.dog_3, R.drawable.dog_4,R.drawable.dog_5,R.drawable.dog_6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        sListView=(ListView)findViewById(R.id.lv);
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
            View view=View.inflate(CollectionActivity.this,R.layout.list_item1,null);//在list_item中有两个id,现在要把他们拿过来
            TextView mTextView=(TextView) view.findViewById(R.id.tv_list);
            ImageView imageView=(ImageView)view.findViewById(R.id.image);
            //组件一拿到，开始组装
            mTextView.setText(names[position]);
            imageView.setBackgroundResource(icons[position]);
            return view;
        }
    }
}