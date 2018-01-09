package com.example.karan.chat.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karan on 1/7/2018.
 */

public class HomePageAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mHomeFragmentList = new ArrayList<>();
    private final List<String>   mHomeFragmentTitleList = new ArrayList<>();

    public HomePageAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title) {
        mHomeFragmentList.add(fragment);
        mHomeFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mHomeFragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mHomeFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mHomeFragmentList.size();
    }
}
