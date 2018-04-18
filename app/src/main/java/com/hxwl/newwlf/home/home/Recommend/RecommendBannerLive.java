package com.hxwl.newwlf.home.home.Recommend;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.HomeRecommendBean;
import com.hxwl.newwlf.modlebean.YueyueHomeBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.HuiGuDetailActivity;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.UIUtils;
import com.hxwl.wulinfeng.view.HoRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Administrator on 2018/1/11.
 * <p>
 * 首页--推荐--直播比赛
 */

public class RecommendBannerLive extends LinearLayout {
    private View view;
    private Context context;
    private HoRecyclerView live_recycler;
    private ArrayList<HomeRecommendBean.DataBean.ScheduleListBean> list=new ArrayList<>();
    private RecommendLiveAdapter adapter;
    private LinearLayout live_linear;
    private HomeRecommendBean.DataBean bean;
    private int iiii;
    public void setBean(HomeRecommendBean.DataBean bean) {
        this.bean = bean;
        list.clear();
        live_linear.removeAllViews();
        list.addAll(bean.getScheduleList());
        initDate();
            for (int i = 0; i < list.size(); i++) {
                View view = new View(context);
                // 正常情况下点都是白色的
                view.setBackgroundResource(R.drawable.dian2);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(14, 14);
                if (i != 0) {
                    lp.leftMargin = 20;
                }
                live_linear.addView(view, lp);
            }
            if(list.size()==1){
                live_linear.setVisibility(GONE);
            }else {
                live_linear.setVisibility(VISIBLE);
            }
            live_linear.getChildAt(0).setBackgroundResource(
                    R.drawable.dian1);

        adapter.notifyDataSetChanged();

//        initDate();
    }

    public RecommendBannerLive(Context context) {
        super(context);
        initView(context);
        iiii=1;
    }


    private void initDate() {
        OkHttpUtils.post()
                .url(URLS.COMMON_USERSUBSCRIBESTATUS)
                .addParams("userId", MakerApplication.instance.getUid())
                .addParams("token", MakerApplication.instance.getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                YueyueHomeBean yueyueHomeBean = gson.fromJson(response, YueyueHomeBean.class);
                                if (yueyueHomeBean.getCode().equals("1000")) {
                                    for (int i = 0; i <list.size() ; i++) {
                                        if (list.get(i).getScheduleState()!=2&&list.get(i).getScheduleState()!=3){
                                            if (yueyueHomeBean.getData().getSubscribeStatusList().get(i).isHasSubscribe()){
                                                list.get(i).setScheduleState(1);
                                            }else {
                                                list.get(i).setScheduleState(0);
                                            }
                                        }

                                    }

                                    adapter.notifyDataSetChanged();
                                }else if (yueyueHomeBean.getCode().equals("2002")||yueyueHomeBean.getCode().equals("2003")){
                                    UIUtils.showToast(yueyueHomeBean.getMessage());
                                    context.startActivity(LoginActivity.getIntent(context));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    private void initView(final Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.recommenlive, this);
        live_recycler= (HoRecyclerView) view.findViewById(R.id.live_recycler);
        live_linear= (LinearLayout) view.findViewById(R.id.live_linear);


        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        live_recycler.setLayoutManager(linearLayoutManager);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(live_recycler);
        adapter=new RecommendLiveAdapter(context,list);
        live_recycler.setAdapter(adapter);

        adapter.setOnItemclickLinter(new RecommendLiveAdapter.OnItemclickLinter() {
            @Override
            public void onItemClicj(int position) {
                if (bean.getScheduleList().get(position).getScheduleState()==2){
                    context.startActivity(HuiGuDetailActivity.getIntent(context,bean.getScheduleList().get(position).getScheduleId()+"",1));
                }else {
                    context.startActivity(HuiGuDetailActivity.getIntent(context,bean.getScheduleList().get(position).getScheduleId()+"",2));
                }
            }
        });

        live_recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItemPosition=linearLayoutManager.findFirstVisibleItemPosition();

                for (int i = 0; i < list.size(); i++) {
                    if (i == firstVisibleItemPosition) {
                        live_linear.getChildAt(i).setBackgroundResource(
                                R.drawable.dian1);
                    } else {
                        live_linear.getChildAt(i).setBackgroundResource(
                                R.drawable.dian2);
                    }
                }

            }
        });


    }


}
