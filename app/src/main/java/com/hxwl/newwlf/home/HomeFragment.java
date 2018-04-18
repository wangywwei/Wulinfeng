package com.hxwl.newwlf.home;


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
import com.google.gson.Gson;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.home.home.FollowFragment;
import com.hxwl.newwlf.home.home.RecommendFragment;
import com.hxwl.newwlf.home.home.zixun.ZiXunFragment;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.HomeOneBean;
import com.hxwl.utils.SPchucunUtils;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.SearchActivity;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.util.CacheUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.UIUtils;
import com.jaeger.library.StatusBarUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/*
* 首页
* */
public class HomeFragment extends BaseFragment {
    public static final String HOME = "HomeFragment";
    public static final String TAG = "home";

    private MyPagerAdapter mAdapter;
    private ArrayList<String> tabnames = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private View view;
    private SlidingTabLayout tl_SlidingTabLayout;
    private ImageView img_search;
    private LinearLayout ll_layout;
    private ViewPager vp;
    private ZiXunFragment ziXunFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home, null);
            initView(view);
            initData();
            loding();
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


    private void loding() {
        if (!SystemHelper.isConnected(getActivity())) {
            Gson gson = new Gson();
            List<String> loding = CacheUtils.readJson(getActivity(), HOME);
            try {
                HomeOneBean bean = gson.fromJson(loding.get(0), HomeOneBean.class);
                if (bean.getCode().equals("1000")) {
                    for (int i = 0; i < bean.getData().size(); i++) {
                        tabnames.add(bean.getData().get(i).getColumnName());
                        ziXunFragment = new ZiXunFragment();
                        ziXunFragment.setColumnId(bean.getData().get(i).getId() + "");
                        fragments.add(ziXunFragment);
                    }
                    tl_SlidingTabLayout.notifyDataSetChanged();
                    mAdapter.notifyDataSetChanged();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //  .addParams("token", MakerApplication.instance.getToken())
        OkHttpUtils.post()
                .url("http://39.106.165.150/index/indexList?userId=1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        UIUtils.showToast("服务器异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                HomeOneBean bean = gson.fromJson(response, HomeOneBean.class);
                                if (bean.getCode().equals("1000")) {
                                    CacheUtils.writeJson(getActivity(), response, HOME, false);
                                    for (int i = 0; i < bean.getData().size(); i++) {
                                        if (bean.getData().get(i).getRank()==1){
                                            tabnames.add(bean.getData().get(0).getColumnName());
                                            fragments.add(new FollowFragment());
                                        }else if (bean.getData().get(i).getRank()==2){
                                            tabnames.add(bean.getData().get(1).getColumnName());
                                            fragments.add(new RecommendFragment());
                                        }else {
                                            tabnames.add(bean.getData().get(i).getColumnName());
                                            ziXunFragment = new ZiXunFragment();
                                            ziXunFragment.setColumnId(bean.getData().get(i).getId() + "");
                                            fragments.add(ziXunFragment);
                                        }

                                    }
                                    try {
                                        tl_SlidingTabLayout.setCurrentTab(1);
                                    }catch (Exception o){

                                    }
                                    tl_SlidingTabLayout.notifyDataSetChanged();
                                    mAdapter.notifyDataSetChanged();

                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(getActivity()));
                                    getActivity().finish();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
     /*   OkHttpUtils.post()
                .url(URLS.HOME_ONE)
                .addParams("token", MakerApplication.instance.getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        UIUtils.showToast("服务器异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                HomeOneBean bean = gson.fromJson(response, HomeOneBean.class);
                                if (bean.getCode().equals("1000")) {
                                    CacheUtils.writeJson(getActivity(), response, HOME, false);
                                    for (int i = 0; i < bean.getData().size(); i++) {
                                        if (bean.getData().get(i).getRank()==1){
                                            tabnames.add(bean.getData().get(0).getColumnName());
                                            fragments.add(new FollowFragment());
                                        }else if (bean.getData().get(i).getRank()==2){
                                            tabnames.add(bean.getData().get(1).getColumnName());
                                            fragments.add(new RecommendFragment());
                                        }else {
                                            tabnames.add(bean.getData().get(i).getColumnName());
                                            ziXunFragment = new ZiXunFragment();
                                            ziXunFragment.setColumnId(bean.getData().get(i).getId() + "");
                                            fragments.add(ziXunFragment);
                                        }

                                    }
                                    try {
                                        tl_SlidingTabLayout.setCurrentTab(1);
                                    }catch (Exception o){

                                    }
                                    tl_SlidingTabLayout.notifyDataSetChanged();
                                    mAdapter.notifyDataSetChanged();

                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(getActivity()));
                                    getActivity().finish();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });*/
    }

    private void initData() {


        mAdapter = new MyPagerAdapter(getChildFragmentManager(), fragments, tabnames);
        vp.setAdapter(mAdapter);

        tl_SlidingTabLayout.setViewPager(vp);



    }


    private void initView(View view) {
        tl_SlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.tl_SlidingTabLayout);
        img_search = (ImageView) view.findViewById(R.id.img_search);
        ll_layout = (LinearLayout) view.findViewById(R.id.ll_layout);
        vp = (ViewPager) view.findViewById(R.id.vp);
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPchucunUtils.setSousuoType(getActivity(), "0");
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }


}
