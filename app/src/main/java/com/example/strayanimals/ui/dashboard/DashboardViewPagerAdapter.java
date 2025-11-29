package com.example.strayanimals.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.strayanimals.ui.SmartFragmentStatePagerAdapter;

import java.util.List;

public class DashboardViewPagerAdapter extends SmartFragmentStatePagerAdapter {
    private String[] tabs;
    private List<DashboardTab> tabFragmentList;

    public DashboardViewPagerAdapter(FragmentManager fragmentManager, String[] tabs, List<DashboardTab> fragmentList) {
        super(fragmentManager);
        this.tabs = tabs;
        tabFragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return tabFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return tabFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) { return tabs[position]; }
}
