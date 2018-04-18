package com.hxwl.wulinfeng.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.mobstat.StatService;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.SearchActivity;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.bean.SaiChengType;
import com.hxwl.wulinfeng.bean.ZiXunBean;
import com.hxwl.wulinfeng.net.AppClient;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.ICallback;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.UIUtils;
import com.tendcloud.tenddata.TCAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

import static com.hxwl.wulinfeng.R.id.img_search;
import static com.hxwl.wulinfeng.R.layout.frag_shouye;

/**
 * Created by Allen on 2017/5/26.
 */

public class SaiChengFragment extends BaseFragment implements OnTabSelectListener {

    private View frag_saicheng;
    private ViewPager vp;
    private SlidingTabLayout tl_SlidingTabLayout;
    private SaiChengFragment.MyPagerAdapter mAdapter;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<SaiChengType> listData = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();
    private RelativeLayout rl_empty;
    private TextView tv_reconnection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (frag_saicheng == null) {
            frag_saicheng = inflater.inflate(R.layout.frag_saicheng, container, false);
            ButterKnife.bind(this, frag_saicheng);
            initView();
            initData();
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) frag_saicheng.getParent();
            if (parent != null) {
                parent.removeView(frag_saicheng);
            }
        }
        AppUtils.setTitle(getActivity());
        return frag_saicheng;
    }
    private void initView() {
        rl_empty = (RelativeLayout) frag_saicheng.findViewById(R.id.rl_empty);
        ImageView img_search = (ImageView) frag_saicheng.findViewById(R.id.img_search);
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatService.onEvent(getActivity(),"ScheduleSearch","赛程搜索",1);
                TCAgent.onEvent(getActivity(),"ScheduleSearch","赛程搜索");
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        vp = (ViewPager) frag_saicheng.findViewById(R.id.vp);
        tl_SlidingTabLayout = (SlidingTabLayout) frag_saicheng.findViewById(R.id.tl_SlidingTabLayout);
        tv_reconnection = (TextView) frag_saicheng.findViewById(R.id.tv_reconnection);
        tv_reconnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData() ;
            }
        });
    }

    private void initData() {
        if (!SystemHelper.isConnected(getActivity())) {
            UIUtils.showToast("请检查网络");
            rl_empty.setVisibility(View.VISIBLE);
            vp.setVisibility(View.GONE);
            return ;
        }
        rl_empty.setVisibility(View.GONE);
        vp.setVisibility(View.VISIBLE);
        initTabItem();
        int select = getResources().getColor(R.color.rgb_BA2B2C);
        int unselect = getResources().getColor(R.color.shouye_tab_unline);
        //设置选中字体的颜色
        tl_SlidingTabLayout.setTextSelectColor(select);
        tl_SlidingTabLayout.setTextUnselectColor(unselect);
        tl_SlidingTabLayout.setIndicatorColor(select);
        //指示器的宽度
        tl_SlidingTabLayout.setIndicatorWidth(18);
        tl_SlidingTabLayout.setIndicatorHeight(2);
        tl_SlidingTabLayout.setIndicatorCornerRadius(30);
//        tl_SlidingTabLayout.setIndicatorMargin(0,0,0,5) ;
        tl_SlidingTabLayout.setOnTabSelectListener(this);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                releaseVideoRes(mVideo,mToutiao);
                Fragment frag = mFragments.get(position);
                if (frag instanceof ZuiXinFragment) {
                    ((ZuiXinFragment) frag).initData(1);
                } else if (frag instanceof ZuiXinFragment) {
                    ((ZuiXinFragment) frag).initData(1);
                } else if (frag instanceof ShiPinFragment) {
                    ((ShiPinFragment) frag).initData(1);
                } else if (frag instanceof TuJiFragment) {
                    ((TuJiFragment) frag).initData(1);
                }
                String tab = mTitles.get(position);
                switch (tab.length()) {
                    case 2:
                        tl_SlidingTabLayout.setIndicatorWidth(18);
                        break;
                    case 3:
                        tl_SlidingTabLayout.setIndicatorWidth(35);
                        break;
                    case 4:
                        tl_SlidingTabLayout.setIndicatorWidth(45);
                        break;
                    case 5:
                        tl_SlidingTabLayout.setIndicatorWidth(55);
                        break;
                    default:
                        tl_SlidingTabLayout.setIndicatorWidth(35);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                ToastUtils.showToast(activity,state + " ");
            }
        });
    }

    /**
     * 获取上方条目的数量
     */
    private void initTabItem() {
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                getActivity(), true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    listData.clear();
                    SaiChengType bean = new SaiChengType();
                    bean.setName("全部");
                    listData.add(bean);
                    listData.addAll(JSON.parseArray(result.getData(), SaiChengType.class));
                    mTitles.clear();
                    for (SaiChengType info : listData) {
                        mTitles.add(info.getName());
                    }
                    initFragmentList();
                    tl_SlidingTabLayout.notifyDataSetChanged();
                } else if (result != null && result.getStatus().equals("empty")) {

                } else {
                    UIUtils.showToast("获取赛事列表失败...");
                }
            }
        }, "method=" + NetUrlUtils.saiCheng_type);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("method", NetUrlUtils.saiCheng_type);
        tasker.execute(map);

    }

    /**
     * 动态添加fragment列表
     */
    private void initFragmentList() {
        mFragments.clear();//清除fragmentlist
        if (mTitles != null) {
            for (SaiChengType s : listData) {
                SaiChengQuanFragment fragment = new SaiChengQuanFragment();
                fragment.setType(s);
                mFragments.add(fragment);
            }
        }
        mAdapter = new SaiChengFragment.MyPagerAdapter(getChildFragmentManager());
        vp.setAdapter(mAdapter);
        tl_SlidingTabLayout.setViewPager(vp);

    }

    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onTabReselect(int position) {

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

}
