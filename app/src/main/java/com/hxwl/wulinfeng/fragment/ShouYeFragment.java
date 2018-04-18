package com.hxwl.wulinfeng.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.mobstat.StatService;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.SearchActivity;
import com.hxwl.wulinfeng.adapter.SaiShiTypeAdapter;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.bean.SaiChengType;
import com.hxwl.wulinfeng.net.AppClient;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.ICallback;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.jaeger.library.StatusBarUtil;
import com.tendcloud.tenddata.TCAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;

import static android.R.attr.fragment;
import static com.hxwl.wulinfeng.R.string.ad;
import static com.lecloud.sdk.api.stats.IStatsContext.P;

/**
 * Created by Allen on 2017/5/26.
 */

public class ShouYeFragment extends BaseFragment implements OnTabSelectListener, View.OnClickListener {

    private View frag_shouye;

    private ViewPager vp;
    private SlidingTabLayout tl_SlidingTabLayout;
    private MyPagerAdapter mAdapter;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {"最新", "资讯", "视频", "图集"};
    private ZuiXinFragment mZuixin;
    private ZiXunFragment mZixun;
    private ShiPinFragment mShipin;
    private TuJiFragment mTuji;
    private TextView tv_title;
    private ImageView iv_choose;
    private SaiShiTypeAdapter adapter;

    public String saishiId;
    private RelativeLayout rl_layout;
    private GridView rv_type;
    private ImageView iv_back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (frag_shouye == null) {
            frag_shouye = inflater.inflate(R.layout.frag_shouye, container, false);
            ButterKnife.bind(this, frag_shouye);
            initView();
            initData();
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) frag_shouye.getParent();
            if (parent != null) {
                parent.removeView(frag_shouye);
            }
        }
        StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.shouye_tab), 0);
        return frag_shouye;
    }

    private void initData() {
        //初始化赛事类型
        initSaiShiType();
//        mFragments.clear();
        mZuixin = new ZuiXinFragment();
        mZixun = new ZiXunFragment();
        mShipin = new ShiPinFragment();
        mTuji = new TuJiFragment();
        mFragments.add(mZuixin);
        mFragments.add(mZixun);
        mFragments.add(mShipin);
        mFragments.add(mTuji);
        mAdapter = new MyPagerAdapter(getChildFragmentManager());
        vp.setAdapter(mAdapter);

        tl_SlidingTabLayout.setViewPager(vp);
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
                releaseVideoRes(mZuixin,mShipin);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }
    private void releaseVideoRes(ZuiXinFragment mZuixin,ShiPinFragment mShipin) {
        if(mZuixin != null){
            mZuixin.handleReleaseRes();
        }
        if(mShipin != null){
            mShipin.handleReleaseRes();
        }

    }

    private void initView() {
        iv_back = (ImageView) frag_shouye.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        rl_layout = (RelativeLayout) frag_shouye.findViewById(R.id.rl_layout);
        rl_layout.setVisibility(View.GONE);
        tv_title = (TextView) frag_shouye.findViewById(R.id.tv_title);
        tv_title.setOnClickListener(this);
        iv_choose = (ImageView) frag_shouye.findViewById(R.id.iv_choose);
        iv_choose.setOnClickListener(this);
        vp = (ViewPager) frag_shouye.findViewById(R.id.vp);
        ImageView img_search = (ImageView) frag_shouye.findViewById(R.id.img_search);
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatService.onEvent(getActivity(),"IndexSearch","首页搜索",1);
                TCAgent.onEvent(getActivity(),"IndexSearch","首页搜索" );
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        tl_SlidingTabLayout = (SlidingTabLayout) frag_shouye.findViewById(R.id.tl_SlidingTabLayout);
        adapter = new SaiShiTypeAdapter(getActivity(), listData);
        rv_type = (GridView) frag_shouye.findViewById(R.id.rv_type);
        rv_type.setSelector(new ColorDrawable(Color.TRANSPARENT));// 去掉默认点击背景
        rv_type.setAdapter(adapter);
        rv_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SaiChengType bean;
                if (view instanceof TextView) {
                    bean = (SaiChengType) view.getTag();
                } else {
                    bean = (SaiChengType) view.findViewById(R.id.text).getTag();
                }
                if (bean == null) {
                    return;
                }
                if ("WLF全部".equals(bean.getName())) {
                    saishiId = "";
                    StatService.onEvent(getActivity(),"FilterWLF","赛事筛选-WLF全部",1);
                    TCAgent.onEvent(getActivity(),"FilterWLF","赛事筛选-WLF全部");
                } else {
                    saishiId = bean.getId();
                    StatService.onEvent(getActivity(),"Filter"+saishiId,"赛事筛选-"+bean.getName(),1);
                    TCAgent.onEvent(getActivity(),"Filter"+saishiId,"赛事筛选-"+bean.getName());
                }

                for(SaiChengType info : listData){
                    info.setSelect(false);
                }
                bean.setSelect(true);
                adapter.notifyDataSetChanged();

                tv_title.setText(bean.getName());
                rl_layout.setVisibility(View.GONE);
                iv_choose.setImageResource(R.drawable.triangle_down);
                isshow = !isshow;

                int count = vp.getCurrentItem();
                //判断选择的和现状一样不一样
                String nowType = getType();
                String choseType = bean.getId();
                if(nowType.equals(choseType)){
                    setIsRefresh(false);
                }else{
                    setIsRefresh(true);
                }
                if(getIsRefresh()){
                        if(mZuixin != null){
                            BaseFragment fragment1 = (BaseFragment) mFragments.get(0);
                            fragment1.setType(saishiId);

                            ((ZuiXinFragment)mFragments.get(0)).initData(1);
                            setIsRefresh(false);
                        }
                        if(mZixun != null){
                            BaseFragment fragment2 = (BaseFragment) mFragments.get(1);
                            fragment2.setType(saishiId);

                            ((ZiXunFragment)mFragments.get(1)).resetData();
                            setIsRefresh(false);
                        }
                        if(mShipin != null){
                            BaseFragment fragment3 = (BaseFragment) mFragments.get(2);
                            fragment3.setType(saishiId);

                            ((ShiPinFragment)mFragments.get(2)).resetData();
                            setIsRefresh(false);
                        }
                        if(mTuji != null){
                            BaseFragment fragment4 = (BaseFragment) mFragments.get(3);
                            fragment4.setType(saishiId);

                            ((TuJiFragment)mFragments.get(3)).resetData();
                            setIsRefresh(false);
                        }
                }else{

                }
//                BaseFragment fragment = (BaseFragment) mFragments.get(count);
//                fragment.setType(saishiId);
//                Fragment frag = mFragments.get(count);
//                if (frag instanceof ZuiXinFragment) {
//                    ((ZuiXinFragment) frag).resetData();
//                } else if (frag instanceof ZiXunFragment) {
//                    ((ZiXunFragment) frag).resetData();
//                } else if (frag instanceof ShiPinFragment) {
//                    ((ShiPinFragment) frag).resetData();
//                } else if (frag instanceof TuJiFragment) {
//                    ((TuJiFragment) frag).resetData();
//                }
            }
        });
    }

    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onTabReselect(int position) {

    }

    private boolean isshow = false;

    @Override
    public void onClick(View view) { // tv_title  iv_choose iv_back
        switch (view.getId()) {
            case R.id.iv_back :
            case R.id.tv_title:
            case R.id.iv_choose:
                // 平移渐变
                if (isshow) {
//                    TranslateAnimation sa = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1 , Animation.RELATIVE_TO_SELF, 1,
//                            Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0);
//                    sa.setDuration(500);
//                    rl_layout.startAnimation(sa);
                    rl_layout.setVisibility(View.GONE);
                    iv_choose.setImageResource(R.drawable.triangle_down);
                    isshow = !isshow;
                } else {
                    StatService.onEvent(getActivity(),"NavPulldown","首页导航下拉",1);
                    TCAgent.onEvent(getActivity(),"NavPulldown","首页导航下拉");
//                    TranslateAnimation sa = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1 , Animation.RELATIVE_TO_SELF, 1,
//                            Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);
//                    sa.setDuration(500);
//                    rl_layout.startAnimation(sa);
                    rl_layout.setVisibility(View.VISIBLE);
                    iv_choose.setImageResource(R.drawable.triangle_up);
                    isshow = !isshow;
                }

                break;
            default:
                break;
        }
    }

    private void initSaiShiType() {
        if (!SystemHelper.isConnected(getActivity())) {
            UIUtils.showToast("请检查网络");
            return;
        }
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                getActivity(), true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    listData.clear();
                    SaiChengType type = new SaiChengType();
                    type.setName("WLF全部");
                    type.setId("-1");
                    type.setSelect(true);
                    listData.add(type);
                    listData.addAll(JSON.parseArray(result.getData(), SaiChengType.class));
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
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

    private List<SaiChengType> listData = new ArrayList<>(); //弹窗数据

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
            return mTitles[position];
        }


        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(listData.size()<2){
            initSaiShiType();
        }
        StatService.onPageStart(getActivity(), "首页");
        TCAgent.onPageStart(getActivity(), "首页");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(getActivity(),"首页");
        TCAgent.onPageEnd(getActivity(),"首页");
    }
}
