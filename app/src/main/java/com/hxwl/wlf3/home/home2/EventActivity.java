package com.hxwl.wlf3.home.home2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.hxwl.wlf3.home.remenhot.GenDuoActivity;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/*
*  赛事赛程动态
* */
public class EventActivity extends BaseActivity {

    public static Intent getIntent(Context context){
        Intent intent=new Intent(context,EventActivity.class);
        return intent;
    }

    private TabLayout tab;
    private ViewPager pager;
    List<Fragment> list = new ArrayList<>();
    List<String> lists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        initView();

    }

    private void initlist() {
        lists.add("赛事指南");
        lists.add("赛事动态");
        lists.add("讨论区");
    }

    private void initView() {


        tab = (TabLayout) findViewById(R.id.event_tablayout);
        pager = (ViewPager) findViewById(R.id.event_viewpager);
        initlist();
        getlist();
        tab.addTab(tab.newTab().setText(lists.get(0)));
        tab.addTab(tab.newTab().setText(lists.get(1)));
        tab.addTab(tab.newTab().setText(lists.get(2)));
        tab.setupWithViewPager(pager);

        Pageradapters adapter=new Pageradapters(getSupportFragmentManager());
        pager.setAdapter(adapter);
        LinearLayout linearLayout = (LinearLayout) tab.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);

    }
    public List<Fragment> getlist() {
        list.add(new GuideFragment());
        list.add(new DynamicFragment());
        list.add(new CommentFragment());
        return list;
    }







    public class Pageradapters extends FragmentPagerAdapter{
        public Pageradapters(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return lists.get(position);
        }
    }



}
