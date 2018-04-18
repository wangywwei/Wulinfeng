package com.hxwl.newwlf.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.flyco.tablayout.SlidingTabLayout;
import com.hxwl.newwlf.home.MyPagerAdapter;
import com.hxwl.newwlf.schedule.bisairili.BisairiliFragment;
import com.hxwl.newwlf.schedule.cooperation.CooperationFragment;
import com.hxwl.newwlf.schedule.recent.RecentFragment;
import com.hxwl.utils.SPchucunUtils;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.SearchActivity;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
/*
* 赛程
* */

public class ScheduleFragment extends BaseFragment {

    private View view;
    private SlidingTabLayout tl_SlidingTabLayout;
    private ImageView img_search;
    private LinearLayout ll_layout;
    private ViewPager vp;
    private ArrayList<String> tabnames = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    private BisairiliFragment bisairiliFragment;
    private RecentFragment recentFragment;
    private QuanshouFragment quanshouFragment;
    private CooperationFragment cooperationFragment;
    private int diyici;
    public void setTypeCurrent(int type){
        if (diyici==1){
            if (type==1){
                vp.setCurrentItem(0);
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_schedule, null);
            initView(view);
            initData();
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.rgb_BA2B2C), 0);
        return view;
    }

    private String[] titles = {"近期比赛", "比赛日历", "拳手", "合作", "WLF"};

    private void initData() {
        //近期比赛
        tabnames.add(titles[0]);
        recentFragment = new RecentFragment();
        fragments.add(recentFragment);
        //比赛日历
        bisairiliFragment = new BisairiliFragment();
        tabnames.add(titles[1]);
        fragments.add(bisairiliFragment);
        //拳手
        quanshouFragment = new QuanshouFragment();
        tabnames.add(titles[2]);
        fragments.add(quanshouFragment);
        //合作
        cooperationFragment = new CooperationFragment();
        tabnames.add(titles[3]);
        fragments.add(cooperationFragment);
        //WLF
        WIFFragment wifFragment = new WIFFragment();
        tabnames.add(titles[4]);
        fragments.add(wifFragment);

        mAdapter = new MyPagerAdapter(getChildFragmentManager(), fragments, tabnames);
        vp.setAdapter(mAdapter);

        tl_SlidingTabLayout.setViewPager(vp);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        SPchucunUtils.setSousuoType(getActivity(), "1");
                        break;
                    case 1:
                        SPchucunUtils.setSousuoType(getActivity(), "0");
                        break;
                    case 2:
                        SPchucunUtils.setSousuoType(getActivity(), "2");
                        break;
                    case 3:
                        SPchucunUtils.setSousuoType(getActivity(), "0");
                        break;
                    case 4:
                        SPchucunUtils.setSousuoType(getActivity(), "0");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    private void initView(View view) {
        diyici=1;
        tl_SlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.tl_SlidingTabLayout);
        img_search = (ImageView) view.findViewById(R.id.img_search);
        ll_layout = (LinearLayout) view.findViewById(R.id.ll_layout);
        vp = (ViewPager) view.findViewById(R.id.vp);
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);

                startActivity(intent);
            }
        });

    }
}
