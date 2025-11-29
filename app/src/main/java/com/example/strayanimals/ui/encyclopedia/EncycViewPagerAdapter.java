package com.example.strayanimals.ui.encyclopedia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.strayanimals.ui.SmartFragmentStatePagerAdapter;

import java.util.List;

public class EncycViewPagerAdapter extends SmartFragmentStatePagerAdapter {
    private String[] tabs;
    private List<EncycTabFragment> tabFragmentList;


    public EncycViewPagerAdapter(FragmentManager fragmentManager,String[] mtabs, List<EncycTabFragment> mtabFragmentList) {
        super(fragmentManager);

        tabs = mtabs;
        tabFragmentList = mtabFragmentList;

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
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
