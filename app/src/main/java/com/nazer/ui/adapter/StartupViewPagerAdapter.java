package com.nazer.ui.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


public class StartupViewPagerAdapter  extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> fragmentArrayList;

    public StartupViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragmentArrayList = fragments;

    }

    @Override
    public Fragment getItem(int position) {

        return fragmentArrayList.get(position);

    }

    @Override
    public int getCount() {
        return 3;
    }
}