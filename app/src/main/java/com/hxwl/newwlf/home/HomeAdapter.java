package com.hxwl.newwlf.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/9.
 */

public class HomeAdapter extends FragmentPagerAdapter {
    private ArrayList<String> string;
    private ArrayList<Fragment> list;
    public HomeAdapter(FragmentManager fm, ArrayList<Fragment> list, ArrayList<String> string) {
        super(fm);
        this.list=list;
        this.string=string;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list!=null?list.size():0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return string.get(position);
    }

}
