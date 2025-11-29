package com.example.strayanimals.ui.encyclopedia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavArgument;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;

import com.example.strayanimals.R;
import com.example.strayanimals.databinding.FragmentEncyclopediaBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EncyclopediaFragment extends Fragment {
    private String[] tabs = {"问答", "视频", "文章"};
    private List<EncycTabFragment> tabFragmentList = new ArrayList<>();
    private EncyclopediaViewModel encyclopediaViewModel;
    private FragmentEncyclopediaBinding mBinding;
    private List<EncycBean> data = new ArrayList<>();
    private String username;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_encyclopedia, container, false);
        Map<String, NavArgument> mapp = NavHostFragment.findNavController(this).getGraph().getArguments();
        NavArgument navArgument = mapp.get("videoCourseId");
        username=(String) navArgument.getDefaultValue();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding = FragmentEncyclopediaBinding.bind(view);
        TabLayout tabLayout = mBinding.tabLayout;
        ViewPager viewPager = mBinding.viewPager;
        ImageButton searchButton = mBinding.searchButton;
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToAnotherFragment();
            }
        });
        //获取Item列表
        GetEncycItem itemList = new GetEncycItem(getActivity());
        data = itemList.getItem();
        //添加tab
        for (int i = 0; i < tabs.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabs[i]));
            tabFragmentList.add(EncycTabFragment.newInstance(tabs[i],data));
        }

        viewPager.setAdapter(new EncycViewPagerAdapter (getChildFragmentManager (), tabs,tabFragmentList));

        //设置TabLayout和ViewPager联动
        tabLayout.setupWithViewPager(viewPager,false);
    }


    private Activity activity;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    public void changeToAnotherFragment() {
        //如果是用的v4的包，则用getActivity().getSuppoutFragmentManager();
//        FragmentManager fm = getActivity().getSupportFragmentManager();
//        //注意v4包的配套使用
//        Fragment fragment = SearchEncFragment.Companion.newInstance();
//        fm.beginTransaction().replace(R.id.container,fragment).commit();
        Intent intent = new Intent(getActivity(), SearchEncActivity.class);
        startActivity(intent);
    }
}

