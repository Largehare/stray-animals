package com.example.strayanimals.ui.encyclopedia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.strayanimals.R;
import com.example.strayanimals.databinding.FragmentEncycTabBinding;
import com.example.strayanimals.ui.encyclopedia.encinterface.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class EncycTabFragment extends Fragment {
    private FragmentEncycTabBinding mBinding;
    private static List<EncycBean> data = new ArrayList<>();
//    private Activity mActivity;
    public static EncycTabFragment newInstance(String label,List<EncycBean> encycBeans) {
        Bundle args = new Bundle();
        data = encycBeans;
        args.putString("label", label);
        EncycTabFragment fragment = new EncycTabFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public EncycTabFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_encyc_tab, container, false);
        mBinding = FragmentEncycTabBinding.bind(view);
        setHasOptionsMenu(true);

        Context mContext = getActivity();
        RecyclerView recycleView =  mBinding.encycGroup;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recycleView.setLayoutManager(linearLayoutManager);
        String lable = this.getArguments().getString("label");
        EncycAdapter encycAdapter;
        if (lable.equals("问答")){
            encycAdapter = new EncycAdapter(data.subList(0,50),mContext);
        }else if(lable.equals("视频")){
            encycAdapter = new EncycAdapter(data.subList(50,100),mContext);
        }
        else{
            encycAdapter = new EncycAdapter(data.subList(100,150),mContext);
        }

        encycAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@Nullable View view, int position,String url) {
                Intent intent = new Intent(getActivity(),WebViewEncActivity.class);
                //把每个item对应的url传到webview
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
        //recyclerView animator
        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(encycAdapter);
        alphaInAnimationAdapter.setDuration(1000);
        alphaInAnimationAdapter.setInterpolator(new OvershootInterpolator());
        alphaInAnimationAdapter.setFirstOnly(false);
        recycleView.setAdapter(new ScaleInAnimationAdapter(alphaInAnimationAdapter));
        return view;
    }

}