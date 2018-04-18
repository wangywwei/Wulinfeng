package com.hxwl.newwlf.schedule.recent;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.modlebean.RecentJinqiBean;
import com.hxwl.newwlf.modlebean.SaichengHuifaBean;
import com.hxwl.utils.SPchucunUtils;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.UIUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

/*
* 赛程——近期比赛
* */

public class RecentFragment extends BaseFragment {

    private View view;
    private XRecyclerView recent_recycler;
    private RecentAdapter adapter;
    private ArrayList<SaichengHuifaBean.DataBean> list=new ArrayList<>();
    private RecentHeader recentHeader;
    private RecentHeader2 recentHeader2;

    @Override
    public void onResume() {
        super.onResume();
        initData2();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_recent, null);
            initView(view);
            SPchucunUtils.setSousuoType(getActivity(),"1");
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }

        return view;
    }


    private void initData2() {
        OkHttpUtils.post()
                .url(URLS.SCHEDULE_GETLIVESCHEDULELIST)
                .addParams("userId", MakerApplication.instance.getUid())
                .addParams("token",MakerApplication.instance.getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        UIUtils.showToast("服务器异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)){
                            Gson gson = new Gson();
                            try {
                                RecentJinqiBean bean = gson.fromJson(response, RecentJinqiBean.class);
                                if (bean.getCode().equals("1000")){
                                    ArrayList<RecentJinqiBean.DataBean.LiveScheduleListBean> liveScheduleListBeans=new ArrayList<>();
                                    liveScheduleListBeans.addAll(bean.getData().getLiveScheduleList());
                                    recentHeader.setList(liveScheduleListBeans);

                                    ArrayList<RecentJinqiBean.DataBean.NotBeginScheduleListBean> notBeginScheduleListBeans=new ArrayList<>();
                                    notBeginScheduleListBeans.addAll(bean.getData().getNotBeginScheduleList());
                                    recentHeader2.setList(notBeginScheduleListBeans);
                                    
                                    initData();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }



                    }
                });
    }

    private int pageNumber=1;
    private boolean isRefresh=true;
    private void initData() {

        OkHttpUtils.post()
                .url(URLS.SCHEDULE_GETPLAYBACKSCHEDULELIST)
                .addParams("pageNumber",pageNumber+"")
                .addParams("pageSize","10")
                .addParams("token",MakerApplication.instance.getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        UIUtils.showToast("服务器异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)){
                            Gson gson = new Gson();
                            try {
                                SaichengHuifaBean bean = gson.fromJson(response, SaichengHuifaBean.class);
                                if (bean.getCode().equals("1000")){
                                    if (isRefresh){
                                        list.clear();
                                        list.addAll(bean.getData());
                                    }else {
                                        list.addAll(bean.getData());
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }



                    }
                });
    }

    private void initView(View view) {
        recent_recycler = (XRecyclerView) view.findViewById(R.id.recent_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recent_recycler.setLayoutManager(layoutManager);
        adapter=new RecentAdapter(list,getActivity());
        recent_recycler.setAdapter(adapter);
        recent_recycler.setNestedScrollingEnabled(false);
        recentHeader = new RecentHeader(getActivity());
        recent_recycler.addHeaderView(recentHeader);

        recentHeader2 = new RecentHeader2(getActivity());
        recent_recycler.addHeaderView(recentHeader2);

        recent_recycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                isRefresh=true;
                pageNumber=1;
                initData2();
                recent_recycler.refreshComplete();//刷新完成
            }

            @Override
            public void onLoadMore() {
                isRefresh=false;
                pageNumber++;
                initData();
                recent_recycler.refreshComplete();//加载完成
            }
        });
    }
}
