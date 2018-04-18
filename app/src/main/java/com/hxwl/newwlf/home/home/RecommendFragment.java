package com.hxwl.newwlf.home.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.home.home.Recommend.ReCommendGod;
import com.hxwl.newwlf.home.home.Recommend.RecommendAdapter;
import com.hxwl.newwlf.home.home.Recommend.RecommendBanner;
import com.hxwl.newwlf.home.home.Recommend.RecommendBannerLive;
import com.hxwl.newwlf.home.home.Recommend.RecommendTab;
import com.hxwl.newwlf.home.home.Recommend.RecommendTopic;
import com.hxwl.newwlf.home.home.Recommend.RecommendZiXun;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.HomeRecommendBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.util.CacheUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.UIUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2018/1/9.
 *
 * 首页——推荐页面
 */

public class RecommendFragment extends BaseFragment {
    public static final String HOME="RecommendFragment";
    private XRecyclerView xrecyclerview;
    private RecommendAdapter adapter;
    private View view;
    private HomeRecommendBean bean;
    private RecommendBanner recommendBanner;
    private RecommendBannerLive recommendBannerLive;
    private RecommendTab recommendTab;
    private RecommendZiXun recommendZiXun;
    private ReCommendGod reCommendGod;
    private RecommendTopic recommendTopic;

    @Override
    public void onResume() {
        super.onResume();
        loding();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_recommend,null);
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



    private void loding() {
        if (!SystemHelper.isConnected(getActivity())) {
            Gson gson = new Gson();
            List<String> loding=CacheUtils.readJson(getActivity(),HOME);
            try {
                bean = gson.fromJson(loding.get(0), HomeRecommendBean.class);
                if (bean.getCode().equals("1000")){
                    try {
//                        recommendBanner.setBean(bean.getData());
                    }catch (Exception i){

                    }
                    try {
                        recommendTab.setBean(bean.getData());
                    }catch (Exception i){

                    }
                    try {
                        recommendBannerLive.setBean(bean.getData());
                    }catch (Exception i){

                    }
                    try {
                        reCommendGod.setBean(bean.getData());
                    }catch (Exception i){

                    }
                    try {
                        recommendZiXun.setBean(bean.getData());
                    }catch (Exception i){

                    }
                    try {
                        recommendTopic.setBean(bean.getData());
                    }catch (Exception i){

                    }

                    adapter.notifyDataSetChanged();
                }else if (bean.getCode().equals("2002")){
                    UIUtils.showToast(bean.getMessage());
                    startActivity(LoginActivity.getIntent(getActivity()));
                    getActivity().finish();
                }else if (bean.getCode().equals("2003")){
                    UIUtils.showToast(bean.getMessage());
                    startActivity(LoginActivity.getIntent(getActivity()));
                    getActivity().finish();
                }



            }catch (Exception e){
                e.printStackTrace();
            }

            return;
        }

        OkHttpUtils.post()
                .url(URLS.HOME_HOME)
                .addParams("userId", MakerApplication.instance.getUid())
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
                                bean = gson.fromJson(response, HomeRecommendBean.class);
                                if (bean.getCode().equals("1000")){

                                    CacheUtils.writeJson(getActivity(),response,HOME,false);
                                    try {
//                                        recommendBanner.setBean(bean.getData());
                                    }catch (Exception i){

                                    }
                                    try {
                                        recommendTab.setBean(bean.getData());
                                    }catch (Exception i){

                                    }
                                    try {
                                        recommendBannerLive.setBean(bean.getData());
                                    }catch (Exception i){

                                    }
                                    try {
                                        reCommendGod.setBean(bean.getData());
                                    }catch (Exception i){

                                    }
                                    try {
                                        recommendZiXun.setBean(bean.getData());
                                    }catch (Exception i){

                                    }
                                    try {
                                        recommendTopic.setBean(bean.getData());
                                    }catch (Exception i){

                                    }

                                    adapter.notifyDataSetChanged();
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(getActivity()));
                                    getActivity().finish();
                                }



                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }
                });

    }

    private void initView(View view) {

        xrecyclerview= (XRecyclerView) view.findViewById(R.id.xrecyclerview);
        adapter=new RecommendAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrecyclerview.setLayoutManager(layoutManager);
        xrecyclerview.setNestedScrollingEnabled(false);
        xrecyclerview.setAdapter(adapter);

        //推荐banner
        recommendBanner = new RecommendBanner(getActivity());
        xrecyclerview.addHeaderView(recommendBanner);

        //轮播的比赛信息
        recommendBannerLive = new RecommendBannerLive(getActivity());
        xrecyclerview.addHeaderView(recommendBannerLive);

        //轮播TAB
        recommendTab = new RecommendTab(getActivity());
        xrecyclerview.addHeaderView(recommendTab);

        //推荐资讯
        recommendZiXun = new RecommendZiXun(getActivity());
        xrecyclerview.addHeaderView(recommendZiXun);

        //火爆神评
        reCommendGod = new ReCommendGod(getActivity());
        xrecyclerview.addHeaderView(reCommendGod);

        //推荐话题
        recommendTopic = new RecommendTopic(getActivity());
        xrecyclerview.addHeaderView(recommendTopic);


        xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                loding();
                xrecyclerview.refreshComplete();//刷新完成
            }

            @Override
            public void onLoadMore() {
                xrecyclerview.refreshComplete();//加载完成
            }
        });

    }



}
