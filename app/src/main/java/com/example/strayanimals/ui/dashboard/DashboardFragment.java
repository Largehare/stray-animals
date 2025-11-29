package com.example.strayanimals.ui.dashboard;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.strayanimals.PublishActivity;
import com.example.strayanimals.R;
import com.example.strayanimals.data.Post;
import com.example.strayanimals.databinding.FragmentDashboardBinding;
import com.example.strayanimals.ui.post.PostSQLHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private WaterFallAdapter mAdapter;
    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;

    private String[] tabs = {"关注", "推荐"};
    private List<DashboardTab> tabFragmentList = new ArrayList<>();
    private ViewPager viewPager;

    private PostSQLHelper sqlHelper;

    private List<Post> posts;
    private String[] lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum".split(" ");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initTabs();
        addButtonClickEvent();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();
        onCreate(null);
        refreshTabs();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initTabs() {
        TabLayout tabLayout = binding.tabLayout;
        for (int i = 0; i < tabs.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabs[i]));
            tabFragmentList.add(DashboardTab.newInstance(tabs[i]));
        }

        viewPager = binding.viewPager;
        viewPager.setAdapter(new DashboardViewPagerAdapter(getChildFragmentManager(), tabs, tabFragmentList));
        tabLayout.setupWithViewPager(viewPager,false);
    }

    private void refreshTabs() {
        viewPager.setAdapter(new DashboardViewPagerAdapter(getChildFragmentManager(), tabs, tabFragmentList));
    }

    private void addButtonClickEvent() {
        FloatingActionButton mMainButton = getView().findViewById(R.id.floatingActionButton);
        mMainButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PublishActivity.class);
            intent.putExtra("username", getActivity().getIntent().getStringExtra("username"));
            startActivity(intent);
            //addRandomPost();
            //readPosts();
            //initWaterfall();
        });
    }
}