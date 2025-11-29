package com.example.strayanimals.ui.notifications;


import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavArgument;
import androidx.navigation.fragment.NavHostFragment;

import com.example.strayanimals.MainActivity;
import com.example.strayanimals.R;
import com.example.strayanimals.databinding.FragmentNotificationsBinding;
import com.example.strayanimals.ui.encyclopedia.SearchEncActivity;

import java.util.Map;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    private String username;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        Map<String, NavArgument> map = NavHostFragment.findNavController(this).getGraph().getArguments();
        NavArgument navArgument = map.get("videoCourseId");
        String username=(String) navArgument.getDefaultValue();
        TextView name= binding.name;
        name.setText(username);

        LinearLayout trait = binding.donate;
        LinearLayout setting = binding.set;
        LinearLayout collection = binding.collection;
        trait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CollectionActivity.class));
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Setting_Activity.class));
            }
        });
        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CollectionActivity.class));
            }
        });


        return root;
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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    }