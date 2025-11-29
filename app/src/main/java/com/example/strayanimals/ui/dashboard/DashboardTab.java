package com.example.strayanimals.ui.dashboard;

import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.example.strayanimals.data.Post;
import com.example.strayanimals.databinding.FragmentDashboardTabBinding;
import com.example.strayanimals.ui.post.AnimalDetailActivity;
import com.example.strayanimals.ui.post.PostSQLHelper;

import java.util.List;
import java.util.Random;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class DashboardTab extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private WaterFallAdapter mAdapter;
    private FragmentDashboardTabBinding binding;

    private PostSQLHelper sqlHelper;
    private List<Post> posts;
    private String defaultContent = "3个月的小猫找靠谱领养！";

    private String category;

    public DashboardTab() {
        // Required empty public constructor
    }

    public static DashboardTab newInstance(String category) {
        DashboardTab fragment = new DashboardTab();
        Bundle args = new Bundle();
        args.putString("category", category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString("category");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDashboardTabBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        readPosts();
        initWaterfall();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void readPosts() {
        sqlHelper = new PostSQLHelper(getContext());
        try {
            sqlHelper.getReadableDatabase().query("post", null, null, null, null, null, null);
        } catch(Exception e) {
            Log.e("Helper", e.toString());
            sqlHelper.onCreate(sqlHelper.getReadableDatabase()); //创建表单
            Log.i("Helper", "Table created");
            addRandomPost();
        }

        posts = sqlHelper.readSQL();
    }

    private void initWaterfall() {
        mRecyclerView = binding.recyclerViewWaterfall;
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mAdapter = new WaterFallAdapter(getContext(), posts);
        mAdapter.setOnItemClickListener((view, position) -> {
            Intent intent = new Intent(getActivity(), AnimalDetailActivity.class);
            intent.putExtra("index", position);
            startActivity(intent);
        });

        //recyclerView animator
        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(mAdapter);
        alphaInAnimationAdapter.setDuration(1000);
        alphaInAnimationAdapter.setInterpolator(new OvershootInterpolator());
        alphaInAnimationAdapter.setFirstOnly(false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(new ScaleInAnimationAdapter(alphaInAnimationAdapter));
    }

    private void addRandomPost() {
        Location l = new Location("");

        sqlHelper.addPost(new Post(String.valueOf(new Random().nextInt(100)), defaultContent, "c", l, ""));
    }
}