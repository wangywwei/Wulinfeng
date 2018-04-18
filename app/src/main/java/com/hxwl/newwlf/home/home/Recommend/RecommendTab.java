package com.hxwl.newwlf.home.home.Recommend;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxwl.newwlf.home.MyPagerAdapter;
import com.hxwl.newwlf.modlebean.HomeRecommendBean;
import com.hxwl.newwlf.sousuo.CustomViewPager;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.fragment.LiveChatFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/12.
 */

public class RecommendTab extends LinearLayout implements View.OnClickListener {
    private FragmentActivity context;
    private TextView bisaihuifang;
    private TextView jingcaiduizheng;
    private HomeRecommendBean.DataBean bean;
    private TabFragment tabFragment;
    private TabFragment2 tabFragment2;
    private MyPagerAdapter mAdapter;
    private LinearLayout huifang;
    private FragmentTransaction transaction;

    public void setBean(HomeRecommendBean.DataBean bean) {
        this.bean = bean;
        if (tabFragment==null){
            tabFragment=new TabFragment();
            tabFragment.setBean(bean);
        }else {
            tabFragment.setBean(bean);
        }
        if (tabFragment2==null){
            tabFragment2=new TabFragment2();
            tabFragment2.setBean(bean);
        }else {
            tabFragment2.setBean(bean);
        }
    }

    public RecommendTab(FragmentActivity context) {
        super(context);
        initView(context);
    }
    private void initView(final FragmentActivity context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.recommen_tab, this);
        bisaihuifang = (TextView) view.findViewById(R.id.bisaihuifang);
        huifang = (LinearLayout) view.findViewById(R.id.huifang);
        jingcaiduizheng = (TextView) view.findViewById(R.id.jingcaiduizheng);
        jingcaiduizheng.setOnClickListener(this);
        bisaihuifang.setOnClickListener(this);
        if (tabFragment2==null) {
            tabFragment2 = new TabFragment2();
        }
        if (tabFragment==null) {
            tabFragment=new TabFragment();
        }
        transaction = context.getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.tab_frame, tabFragment);
        transaction.add(R.id.tab_frame, tabFragment2).hide(tabFragment2);
        transaction.commit();
    }

    private void hide(FragmentTransaction transaction) {
        if (tabFragment != null) {
            transaction.hide(tabFragment);
        }
        if (tabFragment2 != null) {
            transaction.hide(tabFragment2);
        }
    }
    @Override
    public void onClick(View v) {
        transaction =context.getSupportFragmentManager().beginTransaction();
        hide(transaction);
        switch (v.getId()) {
            case R.id.bisaihuifang:
                jingcaiduizheng.setTextColor(context.getResources().getColor(R.color.rgb_888888));
                bisaihuifang.setTextColor(context.getResources().getColor(R.color.rgb_BA2B2C));
                if (tabFragment == null) {
                    tabFragment = new TabFragment();
                    transaction.add(R.id.tab_frame, tabFragment);
                } else {
                    transaction.show(tabFragment);
                }
                break;
            case R.id.jingcaiduizheng:
                jingcaiduizheng.setTextColor(context.getResources().getColor(R.color.rgb_BA2B2C));
                bisaihuifang.setTextColor(context.getResources().getColor(R.color.rgb_888888));
                if (tabFragment2 == null) {
                    tabFragment2 = new TabFragment2();
                    transaction.add(R.id.tab_frame, tabFragment2);
                } else {
                    transaction.show(tabFragment2);
                }
                break;
        }
        transaction.commit();
    }

}
