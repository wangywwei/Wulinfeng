package com.hxwl.newwlf.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/17.
 */


public class MyPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;
    private ArrayList<String> tabnames;

    public MyPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, ArrayList<String> tabnames) {
        super(fm);
        this.fragments = fragments;
        this.tabnames = tabnames;
    }

    @Override
    public int getCount() {
        return fragments != null ? fragments.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabnames != null ? tabnames.get(position) : null;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }


}

