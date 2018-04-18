package com.hxwl.newwlf.wulin;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxwl.newwlf.home.MyPagerAdapter;
import com.hxwl.newwlf.home.home.FollowFragment;
import com.hxwl.newwlf.home.home.RecommendFragment;
import com.hxwl.newwlf.wulin.arts.HuatiFragment;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.fragment.LiveChatFragment;
import com.hxwl.wulinfeng.fragment.WuLinFragment;

import java.util.ArrayList;
/*
*
* 拳手界面
* */
public class ArtsFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    private ImageView arts_xiaoxi;
    private TextView arts_wulin;
    private TextView arts_huati;

    private WuLinFragment wuLinFragment;
    private HuatiFragment huatiFragment;
    private ViewPager arts_frame;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    private boolean topic;
    public void setTopic(boolean topic) {
        this.topic=topic;
        if (iii==1){
        if (topic){
                arts_frame.setCurrentItem(1);
                arts_wulin.setTextColor(getResources().getColor(R.color.white));
                arts_wulin.setBackground(getResources().getDrawable(R.drawable.shap_wulin));
                arts_huati.setTextColor(getResources().getColor(R.color.rgb_BA2B2C));
                arts_huati.setBackground(getResources().getDrawable(R.drawable.shap_huati2));
        }else {
            arts_frame.setCurrentItem(0);
            arts_wulin.setTextColor(getResources().getColor(R.color.rgb_BA2B2C));
            arts_wulin.setBackground(getResources().getDrawable(R.drawable.shap_wulin2));
            arts_huati.setTextColor(getResources().getColor(R.color.white));
            arts_huati.setBackground(getResources().getDrawable(R.drawable.shap_huati));
        }
        }
    }
    private int iii;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_arts, null);
            initView(view);
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initView(View view) {
        iii=1;
        arts_xiaoxi = (ImageView) view.findViewById(R.id.arts_xiaoxi);
        arts_wulin = (TextView) view.findViewById(R.id.arts_wulin);
        arts_huati = (TextView) view.findViewById(R.id.arts_huati);
        arts_frame = (ViewPager) view.findViewById(R.id.arts_frame);
        arts_xiaoxi.setOnClickListener(this);
        arts_wulin.setOnClickListener(this);
        arts_huati.setOnClickListener(this);

        arts_wulin.setTextColor(getResources().getColor(R.color.rgb_BA2B2C));
        arts_wulin.setBackground(getResources().getDrawable(R.drawable.shap_wulin2));
        arts_huati.setTextColor(getResources().getColor(R.color.white));
        arts_huati.setBackground(getResources().getDrawable(R.drawable.shap_huati));

        wuLinFragment = new WuLinFragment();
        huatiFragment=new HuatiFragment();
        fragments.add(wuLinFragment);
        fragments.add(huatiFragment);
        mAdapter = new MyPagerAdapter(getChildFragmentManager(), fragments, null);
        arts_frame.setAdapter(mAdapter);
        if (topic){
            arts_frame.setCurrentItem(1);
            arts_wulin.setTextColor(getResources().getColor(R.color.white));
            arts_wulin.setBackground(getResources().getDrawable(R.drawable.shap_wulin));
            arts_huati.setTextColor(getResources().getColor(R.color.rgb_BA2B2C));
            arts_huati.setBackground(getResources().getDrawable(R.drawable.shap_huati2));
        }else {
            arts_frame.setCurrentItem(0);
            arts_wulin.setTextColor(getResources().getColor(R.color.rgb_BA2B2C));
            arts_wulin.setBackground(getResources().getDrawable(R.drawable.shap_wulin2));
            arts_huati.setTextColor(getResources().getColor(R.color.white));
            arts_huati.setBackground(getResources().getDrawable(R.drawable.shap_huati));
        }
        arts_frame.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        arts_wulin.setTextColor(getResources().getColor(R.color.rgb_BA2B2C));
                        arts_wulin.setBackground(getResources().getDrawable(R.drawable.shap_wulin2));
                        arts_huati.setTextColor(getResources().getColor(R.color.white));
                        arts_huati.setBackground(getResources().getDrawable(R.drawable.shap_huati));
                        break;
                    case 1:
                        arts_wulin.setTextColor(getResources().getColor(R.color.white));
                        arts_wulin.setBackground(getResources().getDrawable(R.drawable.shap_wulin));
                        arts_huati.setTextColor(getResources().getColor(R.color.rgb_BA2B2C));
                        arts_huati.setBackground(getResources().getDrawable(R.drawable.shap_huati2));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.arts_xiaoxi:

                break;
            case R.id.arts_wulin:
                arts_frame.setCurrentItem(0);
                arts_wulin.setTextColor(getResources().getColor(R.color.rgb_BA2B2C));
                arts_wulin.setBackground(getResources().getDrawable(R.drawable.shap_wulin2));
                arts_huati.setTextColor(getResources().getColor(R.color.white));
                arts_huati.setBackground(getResources().getDrawable(R.drawable.shap_huati));
                break;
            case R.id.arts_huati:
                arts_frame.setCurrentItem(1);
                arts_wulin.setTextColor(getResources().getColor(R.color.white));
                arts_wulin.setBackground(getResources().getDrawable(R.drawable.shap_wulin));
                arts_huati.setTextColor(getResources().getColor(R.color.rgb_BA2B2C));
                arts_huati.setBackground(getResources().getDrawable(R.drawable.shap_huati2));
                break;
        }
    }
}
