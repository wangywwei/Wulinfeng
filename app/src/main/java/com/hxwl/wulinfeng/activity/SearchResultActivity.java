package com.hxwl.wulinfeng.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.mobstat.StatService;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hxwl.utils.SPchucunUtils;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.fragment.SearchSaiChengFragment;
import com.hxwl.wulinfeng.fragment.SearchShiPinFragment;
import com.hxwl.wulinfeng.fragment.SearchTuJiFragment;
import com.hxwl.wulinfeng.fragment.SearchWangQiFragment;
import com.hxwl.wulinfeng.fragment.SearchXuanShouFragment;
import com.hxwl.wulinfeng.fragment.ShiPinFragment;
import com.hxwl.wulinfeng.fragment.ShouYeFragment;
import com.hxwl.wulinfeng.fragment.TuJiFragment;
import com.hxwl.wulinfeng.fragment.ZiXunFragment;
import com.hxwl.wulinfeng.fragment.ZuiXinFragment;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.List;

import static com.hxwl.wulinfeng.R.id.et_search;
import static com.hxwl.wulinfeng.R.id.gr_above;
import static com.hxwl.wulinfeng.R.id.iv_clear;
import static com.hxwl.wulinfeng.R.id.rl_above;

/**
 * Created by Allen on 2017/6/28.
 * 搜索结果界面
 */
public class SearchResultActivity extends BaseActivity implements View.OnClickListener, OnTabSelectListener {

    private String keyword; // 搜索关键字
    private static final String HIST_RECORD = "histrecord";
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {"对阵",  "资讯", "话题", "选手" ,"赛程" };
    private ViewPager vp;
    private List<String> listDataUp = new ArrayList<>();
    private SearchSaiChengFragment searchSaiChengFragment;
    private SearchTuJiFragment searchTuJiFragment;
    private SearchWangQiFragment searchWangQiFragment;
    private SearchShiPinFragment searchShiPinFragment;
    private SearchXuanShouFragment searchXuanShouFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchresult_activity);
        AppUtils.setTitle(SearchResultActivity.this);
        keyword = getIntent().getStringExtra("keyword");
        sp = getSharedPreferences(HIST_RECORD,MODE_PRIVATE);
        initView();
        initData();

    }

    private void initData() {
        for (Fragment fragment : mFragments){
            BaseFragment fragments = (BaseFragment)fragment;
            fragments.setKeyword(keyword);
        }
    }
    private String searchText;
    private void initView() {
        TextView cancle = (TextView) findViewById(R.id.cancle);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView iv_clear = (ImageView) findViewById(R.id.iv_clear);
        iv_clear.setOnClickListener(this);

        final EditText et_search = (EditText) findViewById(R.id.et_search);
        et_search.setText(keyword);
        et_search.setSelection(keyword.length());
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
		            /*隐藏软键盘*/
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    if(inputMethodManager.isActive()){
                        inputMethodManager.hideSoftInputFromWindow(SearchResultActivity.this.getCurrentFocus().getWindowToken(), 0);
                    }
                    searchText = et_search.getText().toString();
                    if(TextUtils.isEmpty(searchText)){
                        return false;
                    }
                    listDataUp.add(searchText) ;
                    SaveTextToSp(listDataUp) ;
                    searchShiPinFragment.setKeyword(searchText);
                    searchWangQiFragment.setKeyword(searchText);
                    searchTuJiFragment.setKeyword(searchText);
                    searchXuanShouFragment.setKeyword(searchText);
                    searchSaiChengFragment.setKeyword(searchText);

                    return true;
                }
                return false;
            }
        });
        /*
        * 对阵
        * */
        searchShiPinFragment = new SearchShiPinFragment(keyword);
        mFragments.add(searchShiPinFragment);
        /*
        * 资讯
        * */
        searchWangQiFragment = new SearchWangQiFragment(keyword);
        mFragments.add(searchWangQiFragment);
        /*
        * 话题
        * */
        searchTuJiFragment = new SearchTuJiFragment(keyword);
        mFragments.add(searchTuJiFragment);
        /*
        * 选手
        * */
        searchXuanShouFragment = new SearchXuanShouFragment(keyword);
        mFragments.add(searchXuanShouFragment);
        /*
        * 赛程
        * */
        searchSaiChengFragment = new SearchSaiChengFragment(keyword);
        mFragments.add(searchSaiChengFragment);

        vp = (ViewPager)findViewById(R.id.vp);
        SlidingTabLayout tl_SlidingTabLayout = (SlidingTabLayout)findViewById(R.id.tl_SlidingTabLayout);

        MyPagerAdapter mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);

        tl_SlidingTabLayout.setViewPager(vp);
        int select = getResources().getColor(R.color.rgb_BA2B2C);
        int unselect = getResources().getColor(R.color.rgb_222222);
        //设置选中字体的颜色
        tl_SlidingTabLayout.setTextSelectColor(select);
        tl_SlidingTabLayout.setTextUnselectColor(unselect);
        tl_SlidingTabLayout.setIndicatorColor(select);
        //指示器的宽度
        tl_SlidingTabLayout.setIndicatorWidth(0);
        tl_SlidingTabLayout.setIndicatorHeight(0) ;
        tl_SlidingTabLayout.setIndicatorCornerRadius(0) ;

        tl_SlidingTabLayout.setOnTabSelectListener(this);
        if(SPchucunUtils.getSousuoType(this).equals("1")){
            vp.setCurrentItem(4);
        }else if(SPchucunUtils.getSousuoType(this).equals("2")){
            vp.setCurrentItem(3);
        }else{
            vp.setCurrentItem(0);
        }
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
//                ToastUtils.showToast(activity,state + " ");
            }
        });
    }
    private SharedPreferences sp;
    //保存sp历史记录
    private void SaveTextToSp(List<String> listdata) {
        if(sp != null ){
            sp.edit().putString("histjson", JSON.toJSONString(listdata)).commit() ;
        }
    }
    //加载本地历史记录文件
    private void initLocaData() {
        String hisjson = sp.getString("histjson","");
        if(TextUtils.isEmpty(hisjson)){
            SaveTextToSp(listDataUp);
            return ;
        }
        listDataUp.addAll(JSON.parseArray(hisjson,String.class));
        SaveTextToSp(listDataUp);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case iv_clear :
                finish();
                break ;
            default:
                break;
        }
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
            return mTitles[position];
        }



        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onTabReselect(int position) {

    }
    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(this, "搜索结果");
        TCAgent.onPageStart(this, "搜索结果");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(this,"搜索结果");
        TCAgent.onPageEnd(this,"搜索结果");
    }
}
